from django.conf.urls import url, include
from presence import views
from presence import pdfexport

urlpatterns = [
    url(r'^mark$',views.mark),
    url(r'^get$',views.get_attendance),
    url(r'^schedule/pdf', pdfexport.schedulepdf),
    url(r'^schedule$', views.schedule)
]
