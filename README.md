
# SCM

A web Application used to maintain all the user contact details easily in the cloud, User has to be login usgin useremail and password OR by google signin OR by LinkedIn signin process. If user is creating a new account then verification mail will be sent to the user email address, use has to verify the email account before login with the credencials.


## Clone and Setup

git clone repo https://github.com/hiteshsardar/SCM

```bash
  cd <SCM_Root project>
  gradlew clean build
  npm install
  RUN the proj as a SpringBoot application
```
    
## Pre-requisites

- Gradle installation and configuration
- Java installation and configuration (java -v 17 or more)
- Node installation and configuration (node -v 20)
- Any code Ide for java (intellij idea/Eclipse)
## About

Smart Contact Manager is a web Application creted to maintain all the user's contact details and user can export the contact details to excel sheet. In this progect, many technologies have used **Java SpringBoot, Sprint Security, OAuth2 client security to loging using third party application, Flowbite with Tailwind CSS for the UI designing, Cloudnary for storing the picture on the cloud and Mongodb to store all the user details. User can access all the API's like maintain contact details after successful login.**

#### Steps
- Once the project successfully deployed and starts running, UI will be shown http://localhost:8089/login
- You can login through Google Signin process OR LinkedIn Signin Process.
- Else, you have to create a accound and once account created you have to verify the account throung verification mail.
- Once successful verification you will be redirect to login page, then you can login with your credentials.
- After successful login, you will be redirect to the **HOME SCM** page where you can do all the operations.
## Feedback

If you have any feedback, please reach out to me at sardarhitesh1998@gmaail.com