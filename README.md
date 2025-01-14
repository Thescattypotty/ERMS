### Title: Employee Records Management System

#### Context: Develop an internal Employee Records Management System to centralize the management of employee data across departments.

#### Requirements:
1. Employee Data: The system should manage the following employee attributes:
Full Name, Employee ID, Job Title, Department, Hire Date, Employment Status, Contact Information, Address

2. User Roles and Permissions:
- HR Personnel: Can perform CRUD (Create, Read, Update, Delete) operations on all employee data.
- Managers: Can view and update specific details for employees within their department & Update Employee Data (Limited)
- Managers can edit specific attributes of employees within their department
- Administrators: Full system access, including configuration settings and managing user permissions

3. Audit Trail: Log all changes made to employee records, including who made the change and when.

4. Search and Filtering:
- Allow users to search for employees by name, ID, department, or job title.
- Provide filtering options based on employment status, department, and hire date.

5. Validation Rules: Ensure all employee records comply with predefined validation rules (e.g., valid email format, unique employee ID).

6. Reporting: Generate basic reports

Instructions:
- Build a RESTful API with a Swagger OpenAI documentation
- Use Java 17, Oracle SQL and for the backend Docker
- Develop a Swing-based desktop UI
- Use MigLayout and GridBagLayout for the interface
- Write robust unit and integration tests using JUnit and Mockito, and validate API endpoints a Postman Collection file
- Maintain documentation in Markdown files and document your backlog of tasks also in a Markdown file
- Create a Docker image that can be executed to test the application locally
