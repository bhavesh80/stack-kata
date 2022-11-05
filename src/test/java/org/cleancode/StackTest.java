package org.cleancode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StackTest {

    private Stack stack;

    @BeforeEach
    void setUp() {
        stack = BoundedStack.Make(2);
    }

    @Test
    void NewlyCreatedStacks_ShouldBeEmpty() throws Exception {
        assertThat(stack.isEmpty()).isTrue();
        assertThat(stack.getSize()).isZero();
    }

    @Test
    void AfterOnePush_StackSizeShouldBeOne() throws Exception {
        stack.push(1);
        assertThat(stack.getSize()).isEqualTo(1);
        assertThat(stack.isEmpty()).isFalse();
    }


    @Test
    void AfterOnePushAndOnePop_StackShouldBeEmpty() throws Exception {
        stack.push(1);
        stack.pop();
        assertThat(stack.getSize()).isZero();
        assertThat(stack.isEmpty()).isTrue();
    }

    @Test()
    void WhenPushedPastLimit_StackOverflows() throws Exception {

        stack.push(1);
        stack.push(1);
        assertThatThrownBy(() -> stack.push(1))
                .isInstanceOf(BoundedStack.Overflow.class);
    }


    @Test
    void WhenEmptyStackIsPopped_ShouldThrowUnderFlow() throws Exception{
        assertThatThrownBy(()-> stack.pop())
                .isInstanceOf(BoundedStack.Underflow.class);
    }

    @Test
    void WhenOneIsPushed_OneIsPopped() throws Exception{
        stack.push(1);
        assertThat(stack.pop()).isEqualTo(1);
    }

    @Test
    void WhenOneAndTwoArePushed_TwoAndOneArePopped() throws Exception{
        stack.push(1);
        stack.push(2);
        assertThat(stack.pop()).isEqualTo(2);
        assertThat(stack.pop()).isEqualTo(1);
    }

    @Test
    void WhenCreatingStackWithNegativeSize_ShouldThrowIllegalCapacity() throws Exception{
        assertThatThrownBy(()-> {
                BoundedStack.Make(-1);
        }).isInstanceOf(BoundedStack.IllegalCapacity.class);
    }

    @Test
    void WhenCreatingStackWithZeroCapacityAnyPush_ShouldOverFlow() throws Exception{
        assertThatThrownBy(()->{
            stack = BoundedStack.Make(0);
            stack.push(1);
        }).isInstanceOf(BoundedStack.Overflow.class);
    }

    @Test
    void WhenOneIsPushed_OneIsOnTop() {
        stack.push(1);
        assertThat(stack.top()).isEqualTo(1);
    }

    @Test
    void WhenStackIsEmpty_TopThrowsEmtpy() {

        assertThatThrownBy(()->{
            stack.top();
        }).isInstanceOf(Stack.Empty.class);
    }

    @Test
    void WithZeroCapacityStack_TopThrowsEmpty() {
        assertThatThrownBy(()->{
            stack = BoundedStack.Make(0);
            stack.top();
        }).isInstanceOf(Stack.Empty.class);

    }

    @Test
    void GivenStackWithOneTwoPushed_FindOneAndTwo() {
        stack.push(1);
        stack.push(2);
        assertThat(stack.find(1)).isEqualTo(1);
        assertThat(stack.find(2)).isEqualTo(0);
    }

    @Test
    void GivenStackWithNo2_Find2ShouldReturnNull() {
        assertThat(stack.find(2)).isNull();

    }
}
