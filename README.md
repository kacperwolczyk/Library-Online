# Library-Online

Java web application - Online Library (not completed, but main content is working)
You can use this application as administrator and user. Admin account is already created in import.sql file (password is encoded, so use this one: password= "admin").
As admin, you can add new books to library and manage all user's accounts(lend books, enable/disable accounts).
As user, you can borrow books. There are some rules:
- first of all, you have to create reservation which exists one week. If you have reservation, administrator can lend you the book for one month.
- You can't make reseservation and borrow book if this book is already reserved or lended.
- You can extend your term of return for 2 weeks but only one time.
- If you forgot to return book until term, your account become unactive and you are punished: one day after term = 1 pln debt.
- If you want to active your account again, you have to ask administrator and pay your all debt.
- You can't make reservation, extend termn and borrow book if your account is unactive.
- And of course, you don't have to make account if you want to look at book list.

Like i said, my application is not fully completed yet. I have some new ideas to upgrade this app. Also, i will change front-end in the future (this is not my priority now), because this one was created as fast (and simple) as it was possible. 
To create the application I used following:
- Spring(Boot, Core, MVC, Security, Validation)
- JPA
- Thymeleaf template
- MySQL database
- Maven tool

## Some screens:
![screen1](https://user-images.githubusercontent.com/44527128/47709784-60c79180-dc31-11e8-894a-7945ac6a2d88.png)
![screen2](https://user-images.githubusercontent.com/44527128/47709880-9bc9c500-dc31-11e8-9a08-b17c01d2bd24.png)
![screen3](https://user-images.githubusercontent.com/44527128/47709885-9d938880-dc31-11e8-92f8-9736e326822c.png)
![screen4](https://user-images.githubusercontent.com/44527128/47709888-9ec4b580-dc31-11e8-93a6-883908c08222.png)
![screen5](https://user-images.githubusercontent.com/44527128/47709894-a08e7900-dc31-11e8-98d9-522b450763fd.png)
