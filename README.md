
# Vaccine Center (Java)

In order to fight against the given COVID-19 situation, we need to fasten the
process of vaccinating more and more people in India. To fasten the processof
vaccination we need to onboard multiple vaccine centers across the country and
hence we need to build a small system to manage these vaccine centers.

Vaccine Center is a physical location where the vaccines are administered. Each
center can have supply for multiple vaccine types viz. Covishield, Covaxin,
Sputnik, etc.

Vaccine availability ata center is defined as the number of vaccines available,
number of vaccines booked, vaccine type and dose type.

Note- Currently, the time slot Is skipped in the scope of this project.


## Functionalities to be supported

- Add a vaccine center in the system.
- Add availability of vaccines in the center based on type of vaccine and type of dose.
- Update avalablity of vaccines in the center based on type of vaccine and type of dose.
- Remove avallability of vaccines in the center based on type of vaccine and type of dose.
- Remove avallability of vaccines in the center based on type of vaccine and type of dose.
- Book a vaccine slot, given a center id, type of vaccine and type of dose.
- Search vaccine centers by below filter predicates
- Search by vaccine type and dose type (Ex: Search for centers which have Covishield dose1 available)

## Expectations

- Please go through the template code which is shared along with the project before you start your implementation.
- Identify all the remaining entitles and attributes required for the above functionality.
- Please don't create REST APIs or any Ul for the same.
- Please don't use any database or data stores for this. Suitable in-memory data structures can be used such as list, map or dictionary.
- Please test the functionality using unit test cases.
- The focus is on the core functionality and how It is implemented v/'s how it is presented (UI or API structure)
- Use coding best practices (SOLID, DRY, etc.) and ensure that code written is modular & extensible (to accommodate any future changes in functionality)

### Note
- master branch contain the template code
- main branch will contain the working code


## Important Points

- Template code for the project is present in the repository, please go through the entire code before starting.
- Appropriate comments are added for more context & understanding. Please go through the comments as well before starting
- All the high level entities are listed down in the template code.
- Interfaces are provided In the template code, please add implementation details for the same.
- Please make sure to run the test cases after the implementation as overall scoring is based on running test cases as well, apart from the quality of code.
- Submissions will be evaluated based on functional corectness, clean code, use ofbest coding practices & principles.



## Software Instructions

- Git Basic Commands.
- The question(s) requires Java Development Kit 8.


## Use the following commands to work with this project

Run

```bash
  mvn clean spring-boot:run
```
Test

```bash
  mvn clean test
```
Install

```bash
  mvn clean install
```
