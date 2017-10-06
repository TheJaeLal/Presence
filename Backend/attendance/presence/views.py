import json
from django.shortcuts import render
from django.http import HttpResponse
from presence.models import Attendance
from presence.helper import authenticate
from presence.helper import date_from_string

# Create your views here.

def mark(request):
	if request.method == 'POST':
		# print(request.POST)
		dic = json.loads(request.POST["mark"])
		for item in dic["students"]:
			roll,time = item.split('_')
			roll = int(roll)
			stud = authenticate(roll,time,dic["time"])
			if stud:
				a = Attendance(lecture_id=int(dic["tid"]),date=date_from_string(dic["date"]),student=stud)
	return HttpResponse("OK")