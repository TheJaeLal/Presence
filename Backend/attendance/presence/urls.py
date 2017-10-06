from django.conf.urls import url, include
from presence import views

urlpatterns = [
	url(r'^mark$',views.mark),
]