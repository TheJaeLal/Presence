from presence.models import User
from presence.models import Faculty
from presence.models import Student

class MyCustomBackend(object):

    def authenticate(self, username = None, password = None, type=None):

        #Check the username, password and return a user
        try:
            #Try getting the user with given username
            user = User.objects.get(username=username)

            #valid_password=True if passwords match
            valid_password = user.password==password

            #If User is a Faculty
            if type==1:
                valid_type = Faculty.objects.get(user=user)

            #If user is a student
            elif type==2:
                valid_type = Student.objects.get(user=user)

            if valid_password and valid_type:
                return user,valid_type

            #Invalid password or user_type
            else:
                return None,None

        except User.DoesNotExist:
            return None,None

    def authenticate(self,token=None):
        #Check the token and return a user

        #valid token and user is a Faculty
        user_type = token[1]

        try:
            if token and user_type=='1':
                faculty = Faculty.objects.get(token=token)
                return faculty

            elif token and user_type=='2':
                student = Student.objects.get(token=token)
                return student

        except: User.DoesNotExist
            return None

    # Required for the backend to work properly

    def get_user(self, user_id):
        try:
            return User.objects.get(pk=user_id)
        except User.DoesNotExist:
            return None