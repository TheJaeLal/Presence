from django.http import HttpResponse
from presence import models
from django.core.exceptions import ObjectDoesNotExist, MultipleObjectsReturned
from datetime import datetime
import hashlib


def populatedata(request):
    semesters = [{
        "semester": 1,
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

    departments = [{
        "name": "Computer Engineering",
        "hod":
            {
                "username": "mihir.manek",
                "password": "123456",
                "first_name": "Mihir",
                "last_name": "Manek",
                "email": "mihir.manek@somaiya.edu",
                "contact": 192
            }
    }]
    for department in departments:
        faculty = add_faculty(department["hod"])

        add_department(department["name"], models.Faculty.objects.get(contact=department["hod"]["contact"]))

    add_classes()
    add_divisions()
    courses = [
        {
            "name": "Computer and System Security"
        },
        {
            "name": "Software Project Management"
        },
        {
            "name": "Data Warehouse and Mining"
        },
    ]
    add_courses(courses)

    faculties = [
        {
            "username": "jay.lal",
            "password": "123456",
            "first_name": "Jay",
            "last_name": "Lal",
            "email": "jay.lal@somaiya.edu",
            "contact": 134
        },
        {
            "username": "mehmood.d",
            "password": "123456",
            "first_name": "Mehmood",
            "last_name": "Deshmukh",
            "email": "mehmood.d@somaiya.edu",
            "contact": 452
        }
    ]
    for faculty in faculties:
        print(faculty["first_name"])
        add_faculty(faculty)

    add_lectures()

    now = datetime.now()
    start = now.replace(hour=22, minute=0, second=0, microsecond=0)
    timeslots = [
        {
            "day": "5",
            "start": now.replace(hour=10, minute=30, second=0, microsecond=0),
            "duration": "1",
            "course": "Computer and System Security"
        },
        {
            "day": "5",
            "start": now.replace(hour=11, minute=30, second=0, microsecond=0),
            "duration": "1",
            "course": "Software Project Management"
        },
        {
            "day": "5",
            "start": now.replace(hour=1, minute=15, second=0, microsecond=0),
            "duration": "1",
            "course": "Data Warehouse and Mining"
        },
        {
            "day": "5",
            "start": now.replace(hour=2, minute=15, second=0, microsecond=0),
            "duration": "1",
            "course": "Computer and System Security"
        },
    ]
    populate_time_table(timeslots)

    students = [
        {
            "username": "jerin.john",
            "password": "123456",
            "first_name": "Jerin",
            "last_name": "John",
            "email": "jerin.john@somaiya.edu",
            "dept": "Computer Engineering",
            "rollno": 111,
            "sem": 7,
            "div": "B"
        },
        {
            "username": "a.b",
            "password": "123456",
            "first_name": "a",
            "last_name": "b",
            "email": "a.b@somaiya.edu",
            "dept": "Computer Engineering",
            "rollno": 112,
            "sem": 7,
            "div": "B"
        }
    ]
    add_student(students)
    return HttpResponse("Success")


def add_classes():
    semesters = models.Semester.objects.all()
    for sem in semesters:
        dept = models.Department.objects.get(name="Computer Engineering")
        try:
            classes = models.Class.objects.get(dept=dept, semester=sem)
        except ObjectDoesNotExist as oe:
            cls = models.Class(dept=dept, semester=sem)
            cls.save()


def add_divisions():
    classes = models.Class.objects.all()
    for cls in classes:

        try:
            divisions = models.Division.objects.get(clas=cls, div="A")
        except ObjectDoesNotExist as oe:
            div = models.Division(clas=cls, div="A")
            div.save()

        try:
            divisions = models.Division.objects.get(clas=cls, div="B")
        except ObjectDoesNotExist as oe:
            div = models.Division(clas=cls, div="B")
            div.save()


def add_courses(courses):
    cls = models.Class.objects.get(semester_id=8)
    for course in courses:
        try:
            coursecheck = models.Course.objects.get(name=course["name"])
        except ObjectDoesNotExist as oe:
            course = models.Course(name=course["name"], clas=cls)
            course.save()


def add_semester(semesterinfo):
    sem = models.Semester.objects.get_or_create(semester=semesterinfo)[0]
    sem.semester = semesterinfo
    sem.save()


def add_department(department, hodinfo):
    dep = models.Department.objects.get_or_create(faculty=hodinfo, name=department)[0]
    dep.save()


def add_faculty(faculty):
    userAccount = models.User(email=faculty["email"], first_name=faculty["first_name"], last_name=faculty["last_name"],
                              password=faculty["password"], username=faculty["username"])

    try:
        models.User.objects.get(email=faculty["email"])
    except ObjectDoesNotExist as e:
        userAccount.save()
        faculty = models.Faculty.objects.get_or_create(contact=faculty["contact"], user=userAccount)[0]
        faculty.save()


def add_lectures():
    courses = models.Course.objects.all()
    div = models.Division.objects.get(id=14)
    for course in courses:
        lecturer = models.Faculty.objects.get(id=course.id)
        try:
            models.Lecture.objects.get(div=div, lecturer=lecturer)
        except ObjectDoesNotExist as e:
            lecture = models.Lecture(div=div, lecturer=lecturer, course=course)
            lecture.save()


def populate_time_table(slots):
    for slot in slots:
        try:
            lecture = models.Lecture.objects.get(course__name=slot["course"])
            timetable = models.Timetable.objects.get(lecture=lecture, day=slot["day"], start=slot["start"],
                                                     duration=slot["duration"])

        except ObjectDoesNotExist as oe:
            timetable = models.Timetable(lecture=lecture, day=slot["day"], start=slot["start"],
                                         duration=slot["duration"])
            timetable.save()


def add_student(students):
    for student in students:
        try:
            student = models.Student.objects.get(roll_no=student["rollno"])
        except ObjectDoesNotExist as ode:
            stud_div = models.Division.objects.get(clas__dept__name=student["dept"], div=student["div"],
                                                   clas__semester=student["sem"])
            try:
                user = models.User.objects.get(email=student["email"], first_name=student["first_name"],
                                               last_name=student["last_name"], password=student["password"],
                                               username=student["username"])
            except ObjectDoesNotExist as ode:
                user = models.User(email=student["email"], first_name=student["first_name"],
                                   last_name=student["last_name"], password=student["password"],
                                   username=student["username"])
                user.save()

            stud_obj = models.Student(roll_no=student["rollno"], div=stud_div, user=user)
            stud_obj.save()