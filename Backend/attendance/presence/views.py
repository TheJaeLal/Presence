from django.shortcuts import render
from django.http import HttpResponse
from django.http import JsonResponse
from auth import views

from presence import models
import hashlib

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
                                "duration":course.duration
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
                                "day":course.day,
                            }
                        )
                    response['success'] = True
                    response["message"] = "Successfully Completed the Operation"
                    response['timetable'] = schedule
            elif(auth_result["type"]=="STUDENT"):
                #retrieve student timetable
                print()

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




