test("a basic test example", function() {
    var value = "hello";
    equal(value, "hello", "We expect value to be hello");
});

test("reverse second row of 2 full rows", function() {
    var rowSize = 3;
    var list = [1, 2, 3, 4, 5, 6];

    var result = snakeOrder(list, rowSize);
    deepEqual(result, [1, 2, 3, 6, 5, 4], "The second row should be reversed.");
});

test("reverse when final row incomplete", function() {
    var rowSize = 3;
    var list = [1, 2, 3, 4, 5];

    var result = snakeOrder(list, rowSize);
    deepEqual(result, [1, 2, 3, 5, 4], "The second row should be reversed.");
});

test("don't reverse third row", function() {
    var rowSize = 3;
    var list = [1, 2, 3, 4, 5, 6, 7, 8];

    var result = snakeOrder(list, rowSize);
    deepEqual(result, [1, 2, 3, 6, 5, 4, 7, 8], "The second row should be reversed.");
});