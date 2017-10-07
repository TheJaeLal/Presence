from django.db import models
from django.contrib.auth.models import User

# Create your models here.

class Faculty(models.Model):
	user = models.OneToOneField(User)
	contact = models.CharField(max_length=10)
	token = models.CharField(max_length=41)

class Department(models.Model):
	name = models.CharField(max_length=25)
	faculty = models.ForeignKey(Faculty)

class Semester(models.Model):
	semester = models.IntegerField()

	def get_year(self):
		if self.semester == 1 or self.semester == 2:
			return "FY"
		elif self.semester == 3 or self.semester == 4:
			return "SY"
		elif self.semester == 5 or self.semester == 6:
			return "TY"
		elif self.semester == 7 or self.semester == 8:
			return "LY"

class Class(models.Model):
	dept = models.ForeignKey(Department)
	semester = models.ForeignKey(Semester)

class Division(models.Model):
	clas = models.ForeignKey(Class)
	# divisions = ('A','B')
	div = models.CharField(max_length=1)#,choices=divisions)


class Course(models.Model):
	name = models.CharField(max_length=20)
	clas = models.ForeignKey(Class)

class Lecture(models.Model):
	course = models.ForeignKey(Course)
	lecturer = models.ForeignKey(Faculty)
	div = models.ForeignKey(Division)

class Timetable(models.Model):
	lecture = models.ForeignKey(Lecture)
	WEEKDAYS = (
		('1', 'Monday'),
		('2', 'Tuesday'),
		('3', 'Wednesday'),
		('4', 'Thursday'),
		('5', 'Friday'),
		('6', 'Saturday'),
	)
	day = models.CharField(max_length=1, choices=WEEKDAYS)
	start = models.TimeField()
	duration = models.IntegerField()

class Student(models.Model):
	roll_no = models.IntegerField(primary_key=True)
	user = models.OneToOneField(User)
	div = models.ForeignKey(Division)
	token = models.CharField(max_length=41)
	contact = models.CharField(max_length=10)

class Period(models.Model):
	timetable = models.ForeignKey(Timetable)
	date = models.DateField()

class Attendance(models.Model):
	period = models.ForeignKey(Period,default=0)
	student = models.ForeignKey(Student)
