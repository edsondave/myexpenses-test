package co.edu.uniandes.miso4208.myexpenses.test.bdd;

import co.edu.uniandes.miso4208.myexpenses.model.Transaction;
import co.edu.uniandes.miso4208.myexpenses.test.commons.AndroidDriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class StepDefinitions {

    protected final PodamFactory PODAM_FACTORY = new PodamFactoryImpl();

    private AndroidDriverManager driverManager;
    private Transaction currentTransaction;

    @Before
    public void setup() {
        driverManager = AndroidDriverManager.getInstance();
    }

    @Given("I'm on the new transaction form")
    public void goToNewTransactionForm() {
        driverManager.goToNewTransactionForm();
    }

    @When("I fill the new transaction form")
    public void fillNewTransactionForm() {
        currentTransaction = PODAM_FACTORY.manufacturePojo(Transaction.class);
        driverManager.fillNewTransactionForm(currentTransaction);
        driverManager.clickTickButton();
    }

    @Then("the transaction is created")
    public void verifyTransactionCreated() {
        Assert.assertTrue(driverManager.existsTransactionInMainActivity(currentTransaction));
    }

}