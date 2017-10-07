import hashlib
import datetime
from presence.models import Student
from presence.models import Period
from presence import models
import calendar

EPOCH = datetime.datetime.utcfromtimestamp(0)

def authenticate(roll,hash1,lect_time,date):
	stud = Student.objects.get(roll_no=roll)
	token = stud.token
	print("Token:",token)
	key = token+str(get_epoch(date,lect_time))
	print("HASH1:",hash1)
	hash2 = hash_it(key)[:5]
	print("HASH2:",hash2)
	if hash1 != hash2:
		return None
	return stud

def hash_it(key):
	key = key.encode()
	return hashlib.sha1(key).hexdigest()

def time_to_str(time):
	return str(time.replace(microsecond=0,second=0))[:5]

def date_from_string(string):
	day,month,year = list(map(int,string.split("/")))
	date = datetime.date(day=day,month=month,year=year)
	return date

def time_from_string(string):
	hour,minute = list(map(int,string.split(":")))
	time = datetime.time(hour=hour,minute=minute)
	return time

def get_epoch(date,time):
	date = date_from_string(date)
	time = time_from_string(time)
	dt = datetime.datetime.combine(date,time)
	return int((dt - EPOCH).total_seconds()/10)

def get_day_from_index(index):
	days = ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday']
	return days[int(index)-1]

def get_end_time(time,duration):
    return time.replace(hour=time.hour+int(duration))

def __get_attendance(roll_no,course_name,month_name):
    count_for_div_for_month_for_course = None
    count_for_div_for_month = None
    count_for_student_for_month = None
    count_for_student_for_month_for_course = None 

    #Converting from month name to month_no
    month_no = list(calendar.month_name).index(month_name)

    try:
        student = models.Student.objects.get(roll_no = roll_no)
        # print(student)

    except models.Student.DoesNotExist:
        print("Student does not exist, error occured inside __get_attendance in views.py")
        return None

    try:

        #Filter for student

        attendance_list_for_student = models.Attendance.objects.filter(student=student)
        # print(attendance_list_for_student)

    except models.Attendance.DoesNotExist:
        print("No attendance record for student, means he was present for none of the lectures")
        return 0,0

        #Filter for month
        # attendance_list_for_student_for_month = [a for a in attendance_list_for_student if a.date__month == month_no]

    print("WHat the fuck?")
    attendance_list_for_student_for_month = attendance_list_for_student.filter(period__date__month=month_no)
    count_for_student_for_month = len(attendance_list_for_student_for_month)
    count_for_div_for_month = len(Period.objects.filter(timetable__lecture__div=student.div))
    percentage_for_month = int((count_for_student_for_month/count_for_div_for_month) * 100)

    if course_name==None or course_name.strip()=="":
        return None

    attendance_list_for_student_for_month_for_course = attendance_list_for_student_for_month.filter(period__timetable__lecture__course__name=course_name) 
    count_for_student_for_month_for_course = len(attendance_list_for_student_for_month_for_course)

    count_for_div_for_month_for_course = len(Period.objects.filter(timetable__lecture__course__name=course_name).filter(timetable__lecture__div=student.div))

    percentage_for_month_for_course = int((count_for_student_for_month_for_course/count_for_div_for_month_for_course)*100)

    return percentage_for_month,percentage_for_month_for_course