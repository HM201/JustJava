/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;
import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the encrement button is clicked.
     */
    public void encrement(View view){
        quantity++;
        display(quantity);
    }
    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view){
        if(quantity>0)
            quantity--;
        display(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameTextInput = (EditText) findViewById(R.id.name_text_input);
        String name =  nameTextInput.getText().toString();
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String orderSummary = createOrderSummary(price,hasWhippedCream,hasChocolate,name);
        composeEmail("","JustJava order Summary",orderSummary);
    }
    private void composeEmail(String receiver,String subject,String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(intent.EXTRA_EMAIL,receiver);
        intent.putExtra(intent.EXTRA_SUBJECT,subject);
        intent.putExtra(intent.EXTRA_TEXT,body);
        if(intent.resolveActivity(getPackageManager()) != null)
        startActivity(intent);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays a Given message on the screen
     */
    private void displayMessage(String message){
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }
    /**
    * this method calculates the total price of the order
    */
    private int calculatePrice(boolean hasWhippedCream,boolean hasChocolate){
        int price=0;
        if(hasWhippedCream)
            price += quantity * 1;
        if(hasChocolate)
            price += quantity * 2;
        price += quantity * 5;
        return price;
    }
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate,String name){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream,hasWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_Chocolate,hasChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_Chocolate,hasChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity,quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,NumberFormat.getCurrencyInstance().format(5));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

}