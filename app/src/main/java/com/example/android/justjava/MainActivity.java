package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;



/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int numberOfCoffees = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if(numberOfCoffees == 100){
            setInitial(100);
        }
        else {
        numberOfCoffees += 1; }


        display(numberOfCoffees);

    }

    public void decrement(View view) {
        if(numberOfCoffees == 0){
           setInitial(0);
        }
        else {
        numberOfCoffees -= 1; }

        display(numberOfCoffees);

    }

    public void submitOrder(View view) {
        EditText name = (EditText) findViewById(R.id.name);
        String namea = name.getText().toString();
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox hasChocolateCheckbox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = hasChocolateCheckbox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, namea);



        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "CoffeeOrder" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void setInitial(int number) {
        TextView InitialValue = (TextView) findViewById(R.id.quantity_text_view);
        InitialValue.setText("" + number);

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private int calculatePrice(boolean hasWhipped, boolean hasChocolate) {
        int basePrice = 10;
        if(hasWhipped) {
            basePrice = basePrice + 2;
        }

        if(hasChocolate) {
            basePrice = basePrice + 5;
        }

        return numberOfCoffees * basePrice;
    }





    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\nAdd Whipped Cream: " + addWhippedCream;
        priceMessage += "\nAdd Chocolate: " + addChocolate;
        priceMessage += "\nQuantity: " + numberOfCoffees;
        priceMessage += "\nTotal: Rs " + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;


    }



}