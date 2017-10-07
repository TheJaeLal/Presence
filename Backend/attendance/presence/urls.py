from django.conf.urls import url
from presence import views

urlpatterns = [
    url(r'^schedule', views.schedule)
]
