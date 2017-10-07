from django.conf.urls import url, include
from presence import views
from django.conf.urls import url
from presence import views

urlpatterns = [
    url(r'^schedule', views.schedule),
    url(r'^mark$',views.mark),
]
