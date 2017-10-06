from django.http import HttpResponse
from presence import models
import hashlib

def populatedata(request):
    semesters=[{
            "semester":1,
        },
        {
            "semester": 2,
        },
        {
            "semester": 3,
        },
        {
            "semester": 4,
        },
        {
            "semester": 5,
        },
        {
            "semester": 6,
        },
        {
            "semester": 7,
        },
        {
            "semester": 8,
        },
    ]

    for semester in semesters:
        add_semester(semester["semester"])

    # faculties=[
    #     ,
    # ]
    # for faculty in faculties:
    #     add_faculty(faculty)

    departments=[{
        "name":"Computer Engineering",
        "hod":
            {
                "username": "mihir.manek",
                "password": hashlib.sha1("123456").hexdigest(),
                "first_name": "Mihir",
                "last_name": "Manek",
                "email": "mihir.manek@somaiya.edu",
                "contact": "9876543210"
            }
    }]
    for department in departments:
        add_faculty(department["hod"])
        add_department(department["name"],department["hod"])


def add_semester(semester):
    sem=models.Semester.objects.get_or_create()[0]
    sem.semester=semester
    sem.save()


def add_department(department,hodinfo):
    dep=models.Department.objects.get_or_create(hod=hodinfo,name=department)[0]
    dep.save()


def add_faculty(faculty):
    facultyobj=models.Faculty.objects.get_or_create()[0]
    facultyobj.email=faculty["email"]
    facultyobj.first_name=faculty["first_name"]
    facultyobj.last_name = faculty["last_name"]
    facultyobj.password = faculty["password"]
    facultyobj.contact = faculty["contact"]
    facultyobj.username = faculty["username"]
    facultyobj.save()
