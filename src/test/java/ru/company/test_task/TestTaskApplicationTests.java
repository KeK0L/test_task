package ru.company.test_task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.company.test_task.models.Wallet;
import ru.company.test_task.models.WalletRequest;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTaskApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate template;

	@Test
	public void getBalanceTest() {

		ResponseEntity<Wallet> response = template.getForEntity(String.format("http://localhost:%d/api/v1/wallets/0b1d2afd-d84b-4108-8169-310c9368dcd3", port), Wallet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(new Wallet(UUID.fromString("0b1d2afd-d84b-4108-8169-310c9368dcd3"), 10000L));
	}
	@Test
	public void depositAndWithdrawTest() {
		ResponseEntity<Wallet> getResponse = template.getForEntity(String.format("http://localhost:%d/api/v1/wallets/0b1d2afd-d84b-4108-8169-310c9368dcd3", port), Wallet.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(getResponse.getBody().getWalletId()).isEqualTo(UUID.fromString("0b1d2afd-d84b-4108-8169-310c9368dcd3"));
		Long amount = getResponse.getBody().getAmount();

		WalletRequest walletRequest = new WalletRequest(UUID.fromString("0b1d2afd-d84b-4108-8169-310c9368dcd3"), "DEPOSIT", 3000L);
		ResponseEntity<Wallet> depositResponse = template.postForEntity(String.format("http://localhost:%d/api/v1/wallet", port), walletRequest, Wallet.class);

		assertThat(depositResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(depositResponse.getBody()).isEqualTo(new Wallet(UUID.fromString("0b1d2afd-d84b-4108-8169-310c9368dcd3"), amount + 3000L));

		walletRequest.setOperationType("WITHDRAW");
		ResponseEntity<Wallet> withdrawResponse = template.postForEntity(String.format("http://localhost:%d/api/v1/wallet", port), walletRequest, Wallet.class);

		assertThat(withdrawResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(withdrawResponse.getBody()).isEqualTo(getResponse.getBody());
	}
}
