from reportlab.pdfgen import canvas
from django.http import HttpResponse
from django.http import JsonResponse
from auth import views
from presence import models
from reportlab.platypus import SimpleDocTemplate, Table, TableStyle
from reportlab.lib import colors

def schedulepdf(request):
    response = {}

    if (request.method == 'POST'):

        token = request.POST.get("token")
        day = request.POST.get("day")

        auth_result = views.verify_token(token)
        if (auth_result != None):
            schedule = []
            if (auth_result["type"] == "FACULTY"):
                # retrieve faculties timetable
                if (day != None and int(day) >= 1 and int(day) <= 7):
                    # courses=models.Course.objects.filter(lecture__lecturer=auth_result["object"],lecture__timetable__day=day)
                    lecs = models.Timetable.objects.filter(lecture__lecturer=auth_result["object"], day=day)
                    for course in lecs:
                        schedule.append(
                            {
                                "coursename": course.lecture.course.name,
                                "starttime": course.start,
                                "duration": course.duration,
                                "semester": course.lecture.course.clas.semester.semester,
                                "division": course.lecture.div.div,
                                "department": course.lecture.course.clas.dept.name
                            }
                        )
                    response['timetable'] = schedule
                else:
                    lecs = models.Timetable.objects.filter(lecture__lecturer=auth_result["object"])
                    for course in lecs:
                        schedule.append(
                            {
                                "coursename": course.lecture.course.name,
                                "starttime": course.start,
                                "duration": course.duration,
                                "semester": course.lecture.course.clas.semester.semester,
                                "division": course.lecture.div.div,
                                "department": course.lecture.course.clas.dept.name,
                                "day": course.day,
                            }
                        )
                    response['timetable'] = schedule
            elif (auth_result["type"] == "STUDENT"):
                # retrieve student timetable
                student = auth_result["object"]
                if (day != None and int(day) >= 1 and int(day) <= 7):
                    lecs = models.Timetable.objects.filter(lecture__div=student.div, day=day)
                    for course in lecs:
                        schedule.append(
                            {
                                "coursename": course.lecture.course.name,
                                "starttime": course.start,
                                "duration": course.duration,
                                "semester": course.lecture.course.clas.semester.semester,
                                "division": course.lecture.div.div,
                                "department": course.lecture.course.clas.dept.name,
                                "faculty": course.lecture.lecturer.user.first_name + " " + course.lecture.lecturer.user.last_name
                            }
                        )
                    response['timetable'] = schedule
                else:
                    lecs = models.Timetable.objects.filter(lecture__div=student.div)
                    for course in lecs:
                        schedule.append(
                            {
                                "coursename": course.lecture.course.name,
                                "starttime": course.start,
                                "duration": course.duration,
                                "semester": course.lecture.course.clas.semester.semester,
                                "division": course.lecture.div.div,
                                "department": course.lecture.course.clas.dept.name,
                                "day": course.day,
                                "faculty": course.lecture.lecturer.user.first_name + " " + course.lecture.lecturer.user.last_name
                            }
                        )
                        # Create the HttpResponse object with the appropriate PDF headers.
            response = HttpResponse(content_type='application/pdf')
            response['Content-Disposition'] = 'attachment; filename="schedule.pdf"'

            elements = []
            table_data=[]
            doc = SimpleDocTemplate(response, rightMargin=2, leftMargin=3, topMargin=10, bottomMargin=0)
            headers=[]
            for header in schedule[0]:
                headers.append(header)

            table_data.append(headers)

            for lec in schedule:
                data = []
                for key in lec:
                    data.append(lec[key])
                table_data.append(data)

            table = Table(table_data)

            table.setStyle(TableStyle([
                                   ('INNERGRID', (0, 0), (-1, -1), 0.25, colors.black),
                                   ('BOX', (0, 0), (-1, -1), 0.25, colors.black),
                                   ]))
            elements.append(table)
            doc.build(elements)

            return response
        else:
            return HttpResponse("Invalid")

    else:
        return HttpResponse("Invalid")

