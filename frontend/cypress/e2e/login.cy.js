describe('Login page', () => {
    beforeEach(() => {
        cy.visit('http://localhost/authentication');
    });

    it('should allow a user to log in with correct credentials', () => {
        cy.get('[name="email"]').type('test@example.com');
        cy.get('[name="password"]').type('password123');
        cy.get('button[type="submit"]').click();
        cy.url().should('eq', 'http://localhost/');
    });

    it('should display an error message when incorrect credentials are entered', () => {
        cy.get('[name="email"]').type('test@example.com');
        cy.get('[name="password"]').type('incorrectpassword');
        cy.get('button[type="submit"]').click();
        cy.get('[id="error-message"]').should('contain', 'Invalid email or password');
    });

    it('should redirect to page if clicked on \'Sign up\' tip', () => {
        cy.get('[id="register-tip"]').click();
        cy.url().should('eq', 'http://localhost/register');
    });
});