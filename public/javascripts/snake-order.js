function snakeOrder(array, rowSize) {
    var sizeOfTwoRows = rowSize * 2;
    var twoRowGroups = groupBy(array, sizeOfTwoRows);
    var reverseSecondRow = function(group, index, array) {
       var firstRow = group.slice(0, Math.min(group.length, rowSize));
       var secondRow = [];
       if (group.length > rowSize) {
           secondRow = group.slice(rowSize).reverse();
       }
       return firstRow.concat(secondRow);
    }
    var orderedGroups = twoRowGroups.map(reverseSecondRow);

    return [].concat.apply([], orderedGroups)
}

function groupBy (array, groupSize) {
    var result = [];
    for (i = 0; i < array.length; i += groupSize) {
        result.push(array.slice(i, i + groupSize));
    }
    return result;
}