import json
from django.shortcuts import render
from django.http import HttpResponse
from presence.models import Attendance
from presence.helper import authenticate

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
				a = Attendance(lecture=int(dic["tid"]),date=dic["date"],student=stud)
	return HttpResponse("OK")