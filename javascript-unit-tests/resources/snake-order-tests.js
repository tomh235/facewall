module("snake order tests");

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
    deepEqual(result, [1, 2, 3, 6, 5, 4, 7, 8], "The third row should not be reversed.");
});

test("reverse only second and fourth rows of five rows", function() {
    var rowSize = 3;
    var list = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15];

    var result = snakeOrder(list, rowSize);
    deepEqual(result, [1, 2, 3, 6, 5, 4, 7, 8, 9, 12, 11, 10, 13, 14, 15], "The third row should not be reversed.");
});