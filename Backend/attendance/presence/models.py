from django.db import models

# Create your models here.

class Department(models.Model):
	name = models.CharField(max_length=25)

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


# class Student(models.Model):
# 	roll_no = models.IntegerField(primary_key=True)
# 	name = models.CharField()
# 	class = models.ForeignKey(Class)
