import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HomePageTestWait {

    private static WebDriver driver;

    private By descricaoDosProdutos = By.cssSelector(".product-description a");
    private By botaoAddToCart = By.className("add-to-cart");
    private By mensagemProdutoAdicionadoComSucesso = By.id("myModalLabel");
    String obterMensagem = "Product successfully added to your shopping cart";


    @BeforeAll
    public static void inicializar() {
        WebDriverManager webDriverManager;
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        // Método implícito
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void carregarPaginaInicial() {
        driver.get("https://marcelodebittencourt.com/demoprestashop");
    }


    @AfterAll
    public static void finalizar() {
        driver.quit();
    }


    @Test
    public void testIncluirProdutosNoCarrinho_ProdutoInclusoComSucesso() {
        driver.findElements(descricaoDosProdutos).get(0).click();
        driver.findElement(botaoAddToCart).click();

        // Método Exlícito
              /*  WebDriverWait webDriverWait = new WebDriverWait(driver, 3);
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(mensagemProdutoAdicionadoComSucesso));*/


        // Método FluentWait
        FluentWait fluentWait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        fluentWait.until(ExpectedConditions.visibilityOfElementLocated(mensagemProdutoAdicionadoComSucesso));


        String mensagemProdutoAdicionado = driver.findElement(mensagemProdutoAdicionadoComSucesso).getText();

        assertThat(mensagemProdutoAdicionado.endsWith("Product successfully added to your shopping cart"), is(true));

    }
}
