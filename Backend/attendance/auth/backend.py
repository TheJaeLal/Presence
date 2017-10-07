from presence.models import User,Faculty, Student

def verify_faculty(user):

    if not user:
        return None

    print("***Inside Authenticate")
    #Check the username, password and return a user
    try:

        print("***It is a faculty")
        valid_type = Faculty.objects.get(user=user)
        print("***Valid type =",valid_type)


        if valid_type:
            print("***Everything is perfect")
            return valid_type

    except Faculty.DoesNotExist:
        return None

    return None

def verify_student(user):
    if not user:
        return None
    
    print("***Inside Authenticate")
    #Check the username, password and return a user
    try:

        print("***It is a student")
        valid_type = Student.objects.get(user=user)
        print("***Valid type =",valid_type)


        if valid_type:
            print("***Everything is perfect")
            return valid_type

    except Student.DoesNotExist:
        return None

    return None