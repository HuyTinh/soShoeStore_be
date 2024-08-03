package com.personal.soshoestore_be.service.impl.PaymentServiceImpl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayPalConfig {

	private APIContext apiContext = apiContext();

	public static String mode = "sandbox";

	public static String client_id = "AaXi7K3jvAuxNCjtQWaS2ca9WPDT_MoNEmnfq_KkYrZLvpFcai07i5YOtjVFsxHVUIw36NL4UHLS8_Mu";

	public static String client_secret = "EIwtrbiUk-lDHjIqJ8aoTICgEAHxjQ5D6qXILg3-dZ7NqsVZ_tftCX--4koJWxDVapgOZfXJj4cLIlYs";

	public Map<String, String> paypalSdkConfig() {
		Map<String, String> configMap = new HashMap<String, String>();
		configMap.put("mode", mode);
		return configMap;
	}

	public OAuthTokenCredential oAuthTokenCredential() {
		return new OAuthTokenCredential(client_id, client_secret, paypalSdkConfig());
	}

	public APIContext apiContext() {
		APIContext context = null;
		try {
			context = new APIContext(oAuthTokenCredential().getAccessToken());
			context.setConfigurationMap(paypalSdkConfig());
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return context;
	}

	public Payment createPayment(Double total, String currency, String method, String intent, String description,
			String cancelUrl, String successUrl) throws PayPalRESTException {
		Amount amount = new Amount();
		amount.setCurrency(currency);
		total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));

		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod(method);

		Payment payment = new Payment();
		payment.setIntent(intent);
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);

		return payment.create(apiContext);
	}

	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}
}
