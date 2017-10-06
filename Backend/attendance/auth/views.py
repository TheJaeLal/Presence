from django.shortcuts import render
from django.contrib.auth import authenticate,login
from django.http import HttpResponse
from django.http import JsonResponse
import time, hashlib
from presence.models import Faculty, Student, Course, Attendance
from auth import backend as MyCustomBackend
from django.core.exceptions import ObjectDoesNotExist


# Create your views here.
def user_login(request):
    #Is it a Http Post request?

    if request.method =='POST':

        print("Request method is POST")
        #Get the credentials from the request

        username = request.POST.get('username')
        password = request.POST.get('password')
        user_type = int(request.POST.get('type'))


        #Initialization..
        faculty = None
        student = None
        user = None

        #Returns reference to user object and corresponding student/faculty object if user exists, returns None otherwise..

        if user_type==1:
            print("Type==1")
            print("Trying to authenticate user")
            print("Authentication returned this: ")
            user = MyCustomBackend.verify_faculty(authenticate(username = username, password = password))
            print("Authentication result : user:{}".format(user,faculty))


        elif user_type==2:
            print("Type==2")
            print("Trying to authenticate user")
            user= authenticate(username = username, password = password)
            user = MyCustomBackend.verify_student(authenticate(username = username, password = password))
            print("Authentication result : user:{}".format(user, faculty))

        else:
            print("Type is not equal to 2 nor equal to 1")

        #Creat a response
        response = {
            'success':False,
            'message':"",
            'token':"",
            'profile':None,
            'username':None,
            'type':None,
            'courses': []
        }

        #Check if user exists.. user!=None
        if user:
            print("User is not null")
            #Check if user is active
            if user.is_active:
                print("User is active")
                login(request,user)
                response['success'] = True
                response['message'] = "Login Successful"
                #Creat a string that can be hashed..
                token_generator = username+str(time.time())
                token_generator = token_generator.encode()

                #Generate hash of the token generator string and assign it to response
                response['token'] = hashlib.sha1(token_generator).hexdigest()
                response['username'] = username

                #Check the type of user, faculty or student?
                if faculty:
                    print("User is a faculty")
                    faculty.token = response['token']
                    response['profile'] = faculty
                    response['type'] = 1
                    response['courses'] = [lec.course.name for lec in Lecture.objects.get(lecturer = faculty)]


                elif student:
                    print('User is a student')
                    student.token = response['tokens']
                    response['profile'] = student
                    response['type'] = 2
                    response['courses'] = [lec.course.name for lec in Lecture.objects.get(div=student.div)]

                print("\n******Response Token**********")
                print(response['token'], end ='\n\n')

            #Not active user, i.e account is disabled..
            else:
                print("User's account has been disabled")
                response['success'] = False
                response['message'] = "Account has been Disabled"

        #Failed to authenticate, i.e user does not exist...
        else:
            print("Could not find user")
            print("Invalid login details {0} {1}".format(username,password))
            response['success'] = False
            response['message'] = "Invalid login credentials"

        #Send the response as Json
        return JsonResponse(response)

def verify_token(token):
    user_type=token[0]
    if(user_type==1):
        try:
            faculty=Faculty.objects.get(token=token)
            return {"type":"FACULTY","object":faculty}
        except ObjectDoesNotExist as ode:
            return None
    elif(user_type==2):
        try:
            student=Student.objects.get(token=token)
            return {"type":"STUDENT","object":student}
        except ObjectDoesNotExist as ode:
            return None

