# Development and CI/CD Implementation for Java-Based Library Management System

**Overview**
This Java-based Library Management System provides a robust and comprehensive solution tailored for efficient library management. It incorporates the MVC (Model-View-Controller) design pattern to ensure a clean separation of concerns, enhancing both maintainability and scalability. The system simplifies various library operations such as enrolling new members, cataloging books, and facilitating the loan and return processes. It is meticulously designed to maintain an accurate and persistent record of all library transactions and interactions. Developed collaboratively through a pair programming approach, key features of this system were meticulously crafted in tandem with a teammate, fostering a synergetic development environment that emphasizes code quality and feature reliability. This strategy not only accelerated the development process but also significantly improved problem-solving efficiency, leading to a robust and user-friendly library management system.


**Features Implementation Checklist**
**Basic Functionality:**
Add New Patrons: Implemented.
Allows adding new members with details such as ID, Name, Phone Number, and their borrowed books list.
List All Books: Implemented.
Enables listing of all books stored within the system.
List All Patrons: Implemented.
Facilitates viewing all library patrons.
Issue Books to Patrons: Implemented.
On issuing a book, a Loan object is created, linking the Book, Patron, and the due date.
Return Issued Books: Implemented.
Updates the status of books upon return and removes them from the Patron's borrowed list.
Save System Status: Implemented.
Saves library data in text files upon system closure and reloads this data at startup.
**Intermediate Functionality:**
Additional Properties like added Publisher property to Book and Email property to Patron, including persistence in storage.
Unit Tests Implemented for the same.
Ensures the functionality of the Book and Patron enhancements through unit testing.
**Advanced Functionality:**
Extend GUI Application Implemented for basic GUI functionalities for displaying Patron and Book details, along with Patron addition, are in place.
Data Storing after State Changes: Implemented.
Automatically stores data to file storage after any state changes with error handling and rollback mechanisms.
**High-Level Functionality:**
Remove (Hide) Books/Patrons Implemented so Books and Patrons can be hidden instead of deleted, with affected functions returning only non-deleted items.
Set a Borrowing Limits: Implemented.
Imposes a limit on the number of books a patron can borrow.
GUI Delete Functionality: Implemented.
Allows for the deletion (hiding) of Books and Patrons through the GUI.
Javadoc Documentation: Implemented.
Comprehensive Javadoc documentation for all newly created methods.
**Excellence in Implementation:**
Loan History Record: Implemented.
Maintains a complete loan history for patrons, with details on loan termination and book return dates.
GUI Functionalities: Implemented.
Borrow, renew, and return functionalities are fully integrated into the GUI.
**Contribution**
Contributions are to await until the project content is being restructured until July 2024.
