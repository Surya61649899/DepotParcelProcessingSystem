package main;

import java.util.LinkedList;
import java.util.Queue;

public class QueueOfCustomers {
    // Queue to store customers
    private Queue<Customer> clientQueue;

    // Constructor initializes the customer queue
    public QueueOfCustomers() {
        this.clientQueue = new LinkedList<>();
    }

    // Adds a customer to the queue
    public void addCustomer(Customer client) {
        clientQueue.offer(client);
    }

    // Removes and returns the next customer from the queue
    public Customer removeCustomer() {
        return clientQueue.poll();
    }

    // Returns the next customer in the queue without removing them
    public Customer peekNextCustomer() {
        return clientQueue.peek();
    }

    // Checks if the queue is empty
    public boolean isEmpty() {
        return clientQueue.isEmpty();
    }

    // Returns the number of customers in the queue
    public int getSize() {
        return clientQueue.size();
    }

    // Returns a string representation of the customers in the queue
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Customer client : clientQueue) {
            sb.append(client).append("\n");
        }
        return sb.toString();
    }
}
