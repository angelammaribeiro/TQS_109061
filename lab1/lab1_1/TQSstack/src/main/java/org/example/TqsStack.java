package org.example;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {

    private LinkedList<T> stack = new LinkedList<T>();
    private int bound;
    private boolean isBounded = false;

    public TqsStack(int bound){
        this.bound = bound;
        this.isBounded = true;
    }

    public  TqsStack(){

    }

    public int getBound() {
        return bound;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }

    public boolean isBounded() {
        return isBounded;
    }

    public void setBounded(boolean bounded) {
        isBounded = bounded;
    }

    public LinkedList<T> getStack() {
        return stack;
    }

    public void setStack(LinkedList<T> stack) {
        this.stack = stack;
    }

    public void push(T x){
        if (!isBounded || stack.size() < bound) {
            stack.push(x);
        }else {
            throw new NoSuchElementException("Stack is full");
        }
    }

    public T pop(){
        if (stack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }else{
            return stack.pop();
        }
    }

    public T peek(){
        if(stack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }else {
            return stack.peek();
        }
    }

    public int size(){
        return stack.size();
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    public T popTopN(int n){
        T top = null;

        for (int i = 0; i < n; i++) {
            top = stack.peek();
            stack.pop();
        }
        return top;
    }
}