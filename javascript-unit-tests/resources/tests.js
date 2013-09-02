test("a basic test example", function() {
    var value = "hello";
    equal(value, "hello", "We expect value to be hello");
});

test("reverse second row of 2 full rows", function() {
    var rowSize = 3;
    var list = [1, 2, 3, 4, 5, 6];

    var result = snakeOrder(list, rowSize);
    equal(result, [1, 2, 3, 6, 5, 4], "The second row should be reversed.");
});