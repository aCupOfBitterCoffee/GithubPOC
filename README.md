[TOC]

# GitHub POC

![Platform](https://img.shields.io/badge/platform-Androd-green.svg)
![SDK](https://img.shields.io/badge/SDK-19%2B-green.svg)
![Build](https://img.shields.io/badge/Powered%20by-GoogleAndroid-blue.svg)
![AsPectJ](https://img.shields.io/badge/license-MIT-yellowgreen.svg)
![Chasity Chan](https://img.shields.io/badge/author-ChasityChan-red.svg)


## BDD & Espresso Test Report
This project is built by TDD development, and you can view this [Test Report](https://github.com/aCupOfBitterCoffee/GithubPOC/tree/main/reports) here.
<img width="1082" alt="截屏2025-04-12 21 00 06" src="https://github.com/user-attachments/assets/fedfc066-0eb1-473f-b322-f7b9002dd9bc" />
<img width="1332" alt="截屏2025-04-12 21 00 52" src="https://github.com/user-attachments/assets/1e7c0022-c915-45fb-8898-08206eceedb7" />


## UML  Design Diagram
You can find the [UML of this project](https://github.com/aCupOfBitterCoffee/GithubPOC/blob/main/umlSampleDesign.md) here.



## License
![](https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/License_icon-mit-88x31-2.svg/128px-License_icon-mit-88x31-2.svg.png)

This library is available under the MIT license. See the [LICENSE](https://opensource.org/licenses/MIT) file for more info.



## REquirement

### User Requirement

- User can browse some GitHub content without logon on this application, the content is up to you to define, i.e. popular repository, trending topic etc.
- User can browse a GitHub repository with information up to you to decide but not launching another browser
- User can search repository according to programming languages and order by the stars.
- User can logon to his/her GitHub account and arrives at the profile's repository list. The authentication status survives between application launches. An authenticated user can raise issue to his/her repositories.
- User can logoff and return to the anonymous status.
- The application support both portrait and landscape modes Proper Error Handling, i.e. when network is temporarily down.

### Technical Requirement
- Hosted on GitHub with proper introduction and build/run/test instruction and stable release APK
- Use GitHub Restful APl for your application's data
- Programming Language: Kotlin and API Level 29+
- Declarative UI (jetpack Compose) and proof of usage of some of Android Architect Component ViewModel, LiveData, Coroutine, Navigation Component etc.

### Design Requirements
- Candidates are expected to demonstrate strong architectural design skills.
- Providing a design proposal along with UML diagrams will result in additional credit.

### Testing Requirements
- Candidates are encouraged to apply Test-Driven Development (TDD) practices.
- Higher scores will be awarded for well-structured unit tests that validate the business logic of the application.
- Strong emphasis will be placed on Ul testing using Espresso. Substantial points will be given for comprehensive and effective Ul tests.
- Experience with Behavior-Driven Development (BDD) like cucumber will also be considered a plus.
