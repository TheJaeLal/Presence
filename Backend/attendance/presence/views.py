from django.shortcuts import render
from django.http import HttpResponse
from django.http import JsonResponse
from auth import views

from presence import models

# Create your views here.
def schedule(request):

    #Note: Response should be a dict and not a list.
    #response = []
    response = {}

    if(request.method=='POST'):

        token=request.POST.get("token")
        day=request.POST.get("day")

        auth_result=views.verify_token(token)
        if(auth_result!=None):
            schedule=[]
            if(auth_result["type"]=="FACULTY"):
                #retrieve faculties timetable
                if(day>=1 and day<=7):
                    lecs=models.Timetable.objects.get(lecture__lecturer=auth_result["object"])
                    for lec in lecs:
                        schedule.append(
                            {
                                "coursename":lec.course.name,
                                "starttime":lec.start,
                                "duration":lec.duration
                            }
                        )
                    response['success']=True
                    response["message"]="Successfully Completed the Operation"
                    response['timetable']=schedule
                    return JsonResponse(response)
            elif(auth_result["type"]=="STUDENT"):
                #retrieve student timetable
                print()

        else:
            response["success"]=False;
            response["message"]="Invalid Login"

    else:
        response["success"]=False
        response["message"]="Invalid Request"

def get_student_data(request):
    response = {}
    if request.method=='POST':

        pass
        #Suppose we have student data
        #Suppose we have student time
        #Suppose we have student