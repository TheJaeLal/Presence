import hashlib
import datetime
from presence.models import Student

EPOCH = datetime.datetime.utcfromtimestamp(0)

def authenticate(roll,hash1,lect_time,date):
	stud = Student.objects.get(roll_no=roll)
	token = stud.token
	print("Token:",token)
	key = token+str(get_epoch(date,lect_time))
	print("HASH1:",hash1)
	hash2 = hash_it(key)
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