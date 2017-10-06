from django.shortcuts import render
from django.contrib.auth import authenticate,login
from django.http import HttpResponse
from django.http import JsonResponse
import time, hashlib
from presence.models import Faculty
from presence.models import Student



# Create your views here.
def user_login(request):
    #Is it a Http Post request?

    if request.method =='POST':

        #Get the credentials from the request

        username = request.POST.get('username')
        password = request.POST.get('password')
        user_type = request.POST.get('type')

        faculty = None
        student = None

        #Returns reference to user object and corresponding student/faculty object if user exists, returns None otherwise..

        if type==1:
            user,faculty = authenticate(username = username, password = password, type = user_type)

        elif type==2:
            user,student = authenticate(username = username, password = password, type = user_type)

        #Creat a response
        response = {
            'success':False,
            'message':"",
            'token':""
        }

        #Check if user exists.. user!=None
        if user:
            #Check if user is active
            if user.is_active:

                login(request,user)
                response['success'] = True
                response['message'] = "Login Successful"

                #Creat a string that can be hashed..
                token_generator = username+str(time.time()).encode()

                #Generate hash of the token generator string and assign it to response
                response['token'] = str(user_type)+hashlib.sha1(token_generator).hexdigest()

                #Check the type of user, faculty or student?
                if faculty:
                    faculty.token = response['token']

                elif student:
                    student.token = response['tokens']

                print("\n******Response Token**********")
                print(response['token'], end ='\n\n')

            #Not active user, i.e account is disabled..
            else:
                response['success'] = False
                response['message'] = "Account has been Disabled"

        #Failed to authenticate, i.e user does not exist...
        else:
            print("Invalid login details {0} {1}".format(username,password))
            response['success'] = False
            response['message'] = "Invalid login credentials"

        #Send the response as Json
        return JsonResponse(response)