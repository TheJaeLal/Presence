from django.shortcuts import render
from django.http import HttpResponse
from django.http import JsonResponse
from auth import views

from presence import models
import hashlib
import calendar

# Create your views here.
def schedule(request):
    response = {}

    if(request.method=='POST'):

        token=request.POST.get("token")
        day=request.POST.get("day")

        auth_result=views.verify_token(token)
        if(auth_result!=None):
            schedule=[]
            if(auth_result["type"]=="FACULTY"):
                #retrieve faculties timetable
                if(day!=None and int(day)>=1 and int(day)<=7):
                    #courses=models.Course.objects.filter(lecture__lecturer=auth_result["object"],lecture__timetable__day=day)
                    lecs=models.Timetable.objects.filter(lecture__lecturer=auth_result["object"],day=day)
                    for course in lecs:

                        schedule.append(
                            {
                                "coursename":course.lecture.course.name,
                                "starttime":course.start,
                                "duration":course.duration,
                                "semester":course.lecture.course.clas.semester.semester,
                                "division":course.lecture.div.div,
                                "department":course.lecture.course.clas.dept.name
                            }
                        )
                    response['success']=True
                    response["message"]="Successfully Completed the Operation"
                    response['timetable']=schedule
                else:
                    lecs = models.Timetable.objects.filter(lecture__lecturer=auth_result["object"])
                    for course in lecs:
                        schedule.append(
                            {
                                "coursename": course.lecture.course.name,
                                "starttime": course.start,
                                "duration": course.duration,
                                "semester": course.lecture.course.clas.semester.semester,
                                "division": course.lecture.div.div,
                                "department": course.lecture.course.clas.dept.name,
                                "day":course.day,
                            }
                        )
                    response['success'] = True
                    response["message"] = "Successfully Completed the Operation"
                    response['timetable'] = schedule
            elif(auth_result["type"]=="STUDENT"):
                #retrieve student timetable
                student=auth_result["object"]
                if (day != None and int(day) >= 1 and int(day) <= 7):
                    lecs = models.Timetable.objects.filter(lecture__div=student.div,day=day)
                    for course in lecs:

                        schedule.append(
                            {
                                "coursename":course.lecture.course.name,
                                "starttime":course.start,
                                "duration":course.duration,
                                "semester":course.lecture.course.clas.semester.semester,
                                "division":course.lecture.div.div,
                                "department":course.lecture.course.clas.dept.name,
                                "faculty":course.lecture.lecturer.user.first_name +" "+ course.lecture.lecturer.user.last_name
                            }
                        )
                    response['success']=True
                    response["message"]="Successfully Completed the Operation"
                    response['timetable']=schedule
                else:
                    lecs = models.Timetable.objects.filter(lecture__div=student.div)
                    for course in lecs:
                        schedule.append(
                            {
                                "coursename": course.lecture.course.name,
                                "starttime": course.start,
                                "duration": course.duration,
                                "semester": course.lecture.course.clas.semester.semester,
                                "division": course.lecture.div.div,
                                "department": course.lecture.course.clas.dept.name,
                                "day":course.day,
                                "faculty": course.lecture.lecturer.user.first_name + " " + course.lecture.lecturer.user.last_name
                            }
                        )
                    response['success'] = True
                    response["message"] = "Successfully Completed the Operation"
                    response['timetable'] = schedule

        else:
            response['success']=False;
            response['message']="Invalid Login"

    else:
        response["success"]=False
        response["message"]="Invalid Request"

    return JsonResponse(response)

    

def update_attendance(request):
    response = {
        'success':False
    }

    if request.method=='POST':
        day = request.POST.get('day')
        tid = request.POST.get('tid')
        time = request.POST.get('time')
        student_list = request.POST.get('students')
        if __update_attendance(tid,student_list,time_stamp,day):
           response['success'] = True


    return JsonResponse(response)

def __update_attendance(tid,student_list,time_stamp,day):
    for id in student_list:
        if verify_id(id,time_stamp):


            #increment attendance
            models.Attendance.objects.create(
                student=student,
                lecture=lecture,
                date=date
            )
            #Add student to models.Attendance...



def verify_id(id,time_stamp):
    #Get the roll_no 1st 3 characters
    roll_no = id[:3]
    student_otp = id[3:]

    #Access token is the secret key...
    curr_student = models.Student.objects.get(roll_no = int(roll_no))
    secret_key = curr_student.token

    #Properly format time_stamp
    time_stamp = get_unix(time_stamp)

    otp_generator = str(time_stamp) + str(secret_key)

    #Convert to UTF-8
    otp_generator.encode()

    if student_otp == hashlib.sha1(hash_generator).hex_digest():
        return True

    return False

def get_unix(time):
    #Convert provided time_stamp into unix time..
    #Time is in format hh:mm 24 hour format...

    return time_stamp


#Return attendance in percentage
def get_attendance(request):

    response = {
        'success':False,
        'percentage':None
    }
    if request.method=='POST':
        course_name = request.POST.get('course')
        roll_no = request.POST.get('roll_no')
        month_name = request.POST.get('month')

        if response['percentage'] = __get_attendance(roll_no,course_name,month_name)
            response['success'] = True

    return JsonResponse(response)



#Returns percentage or None
def __get_attendance(roll_no,course_name,month_name):
    percentage_for_course_for_month = None
    percentage_for_month = None

    #Converting from month name to month_no
    month_no = list(calendar.month_name).index(month_name)

    try:
        student = models.Student.objects.get(roll_no = roll_no)

    except models.Student.DoesNotExist:
        print("Student does not exist, error occured inside __get_attendance in views.py")
        return None

    try:

        #Filter for student

        attendance_list_for_student = models.Attendance.filter(student=student)

    except models.Attendance.DoesNotExist:
        print("No attendance record for student, means he was present for none of the lectures")
        return 0

        #Filter for month
        attendance_list_for_student_for_month = [a for a in attendance_list_for_student if a.date__month == month_no]

        percentage_for_month = len(attendance_list_for_student_for_month)/len()


        #Filter for course
        attendance_list_for_student_for_month_for_course = [a for a in attendance_list_for_student if a.lecture.lecture.course.name == course_name]


        attendance_for_course_for_div_for_course = models.Attendance.filter(lecture__lecture__div__div==student.div, lecture__lecture__course__name==course_name)
        percentage = len(attendance_list_for_student_for_month_for_course)/len(attendance_for_course_for_div_for_course)

    return percentage
