package org.example;

import net.thauvin.erik.crypto.CryptoException;
import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public class MyBot extends TelegramLongPollingBot {

    public MyBot() {
        super("6644785668:AAFa6w5npf1MCR_DLu2-NR0Kclytfe2URH0");
    }


    @Override
    public void onUpdateReceived(Update update) {
        var chatId = update.getMessage().getChatId();
        var text = update.getMessage().getText();

        try {
            var message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Hello");

            if (text.equals("/start")) {
                message.setText("Hi");
            } else if (text.equals("btc")) {
                var price = CryptoPrice.spotPrice("BTC");
                message.setText("BTC price: " + price.getAmount().doubleValue());
            } else if (text.equals("eth")) {
                var price = CryptoPrice.spotPrice("ETH");
                message.setText("ETH price: " + price.getAmount().doubleValue());
            } else if (text.equals("doge")) {
                var price = CryptoPrice.spotPrice("DOGE");
                message.setText("Dхм price: " + price.getAmount().doubleValue());
            } else if (text.equals("all")) {
                var price = CryptoPrice.spotPrice("BTC");
                var price1 = CryptoPrice.spotPrice("ETH");
                var price2 = CryptoPrice.spotPrice("DOGE");
                message.setText("BTC price: " + price.getAmount().doubleValue()
                        + "\nETH price: " + price1.getAmount().doubleValue()
                        + "\nDogecoin price: " + price2.getAmount().doubleValue());
            } else {
                try {
                    double amount = Double.parseDouble(text);
                    message.setText(calculateCryptoAmount(amount));
                } catch (NumberFormatException e) {
                    message.setText("Unknown command!");
                }
            }
            execute(message);
        } catch (Exception e) {
            System.out.println("Error!");
        }


    }

    private String calculateCryptoAmount(double amount) throws IOException, CryptoException {
        var btcPrice = CryptoPrice.spotPrice("BTC").getAmount().doubleValue();
        var ethPrice = CryptoPrice.spotPrice("ETH").getAmount().doubleValue();
        var dogePrice = CryptoPrice.spotPrice("DOGE").getAmount().doubleValue();

        double btcAmount = amount / btcPrice;
        double ethAmount = amount / ethPrice;
        double dogeAmount = amount / dogePrice;

        return "For $" + amount + " you can buy:\n"
                + "BTC: " + btcAmount + "\n"
                + "ETH: " + ethAmount + "\n"
                + "DOGE: " + dogeAmount;
    }

    @Override
    public String getBotUsername() {
        return "Prog333_bot";
    }
}


