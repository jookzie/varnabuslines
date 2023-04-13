describe('Register Page', () => {

  beforeEach(() => {
    cy.visit('http://localhost/register');
  });

  it('displays an error message if passwords do not match', () => {
    cy.get('[name="email"]').type('test@example.com');
    cy.get('[name="password"]').type('password123');
    cy.get('[name="confirm-password"]').type('incorrect');
    cy.get('[type="submit"]').click();
    cy.get('[id="error-message"]').should('contain', 'Passwords do not match.');
  });

  // Depends on whether the database contains a user with the credentials below
  it('displays an error message if the email is already in use', () => {
    cy.get('[name="email"]').type('test@example.com');
    cy.get('[name="password"]').type('password123');
    cy.get('[name="confirm-password"]').type('password123');
    cy.get('[type="submit"]').click();
    cy.get('[id="error-message"]').should('contain', 'A user already exists with the same details.');
  });

  // Depends on cleanup, which requires authorization during testing
  it('navigates to the authentication page if the registration is successful', () => {
    cy.get('[name="email"]').type('new@example.com');
    cy.get('[name="password"]').type('password');
    cy.get('[name="confirm-password"]').type('password');
    cy.get('[type="submit"]').click();
    cy.url().should('eq',  'http://localhost/authentication');
  });

  it('should redirect to page if clicked on \'Log in\' tip', () => {
    cy.get('[id="login-tip"]').click();
    cy.url().should('eq', 'http://localhost/authentication');
  });
});