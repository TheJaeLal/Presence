import json
from django.shortcuts import render
from django.http import HttpResponse
from presence.models import Attendance
from presence.models import Period
from presence.helper import authenticate
from presence.helper import date_from_string
from django.http import JsonResponse
from auth import views
from presence.models import __get_attendance

from presence import models
# Create your views here.

def mark(request):
    if request.method == 'POST':
        token=request.POST.get("token")
        auth_result=views.verify_token(token)
        dic = json.loads(request.POST["mark"])
        if auth_result:
            p = Period(timetable_id=int(dic["tid"]),date=date_from_string(dic["date"]))
            p.save()
            for item in dic["students"]:
                roll,time = item.split('_')
                roll = int(roll)
                stud = authenticate(roll,time,dic["time"],dic["date"])
                if stud:
                    # print("Here")
                    a = Attendance(period=p,student=stud)
                    a.save()
            return HttpResponse("OK")
        return HttpResponse("NOT COOL")
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


def get_attendance(request):

    response = {
        'success':False,
        'month':None,
        'course':None
    }
    if request.method=='POST':
        course_name = request.POST.get('course')
        roll_no = request.POST.get('roll_no')
        month_name = request.POST.get('month')

        response['month'],response['course'] = __get_attendance(roll_no,course_name,month_name)
        if response['month'] and response['course']:
            response['success'] = True

    return JsonResponse(response)
