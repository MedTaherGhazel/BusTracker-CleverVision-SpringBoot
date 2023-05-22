const canvas = document.getElementById("canvas");
const context = canvas.getContext("2d");

// Load the map image and bus image
const mapImage = new Image();
mapImage.src = "img/map.png";

const busImage = new Image();
busImage.src = "img/bus.png";

// Define the coordinates of the line that the bus will follow
const lineCoordinates = [
    { x: 517, y: 136 },
    { x: 532, y: 124 },
    { x: 545, y: 115 },
    { x: 554, y: 109 },
    { x: 562, y: 104 },
    { x: 575, y: 91 },
    { x: 587, y: 81 },
    { x: 599, y: 73 },
    { x: 611, y: 77 },
    { x: 624, y: 82 },
    { x: 635, y: 86 },
    { x: 645, y: 100 },
    { x: 651, y: 111 },
    { x: 657, y: 124 },
    { x: 663, y: 135 },
    { x: 675, y: 149 },
    { x: 684, y: 162 },
    { x: 694, y: 173 },
    { x: 696, y: 180 },
    { x: 697, y: 187 },
    { x: 697, y: 195 },
    { x: 697, y: 202 },
    { x: 697, y: 207 },
    { x: 687, y: 211 },
    { x: 678, y: 216 },
    { x: 667, y: 223 },
    { x: 657, y: 231 },
    { x: 642, y: 238 },
    { x: 627, y: 243 },
    { x: 619, y: 255 },
    { x: 611, y: 264 },
    { x: 602, y: 278 },
    { x: 593, y: 291 },
    { x: 586, y: 308 },
    { x: 580, y: 325 },
    { x: 575, y: 344 },
    { x: 570, y: 360 },
    { x: 565, y: 373 },
    { x: 563, y: 377 },
    { x: 562, y: 394 },
    { x: 560, y: 410 },
    { x: 556, y: 427 },
    { x: 551, y: 441 },
    { x: 547, y: 447 },
    { x: 531, y: 472 },
    { x: 514, y: 496 },
    { x: 493, y: 521 },
    { x: 471, y: 544 },
    { x: 468, y: 561 },
    { x: 465, y: 579 },
    { x: 458, y: 608 },
    { x: 453, y: 639 },
    { x: 436, y: 652 },
    { x: 420, y: 660 },
    { x: 404, y: 674 },
    { x: 386, y: 694 },
    { x: 378, y: 709 },
    { x: 371, y: 724 },
    { x: 368, y: 722},
    { x: 370, y: 746 },
    { x: 374, y: 770 },
    { x: 374, y: 788 },
    { x: 374, y: 806 },
    { x: 374, y: 818 },
    { x: 376, y: 829 },
    { x: 380, y: 831 },
    { x: 383, y: 847 },
    { x: 386, y: 864 },
    { x: 384, y: 881 },
    { x: 386, y: 898 },
    { x: 388, y: 925 },
    { x: 392, y: 952 },
    { x: 397, y: 967 },
    { x: 401, y: 983 },
    { x: 413, y: 995 },
    { x: 429, y: 1007 },
    { x: 439, y: 1014 },
    { x: 450, y: 1022 },
    { x: 461, y: 1030 },
    { x: 471, y: 1038 },
    { x: 484, y: 1045 },
    { x: 496, y: 1053 },
    { x: 505, y: 1053 },
    { x: 514, y: 1053 },
    { x: 539, y: 1055 },
    { x: 566, y: 1056 },
    { x: 581, y: 1055 },
    { x: 596, y: 1053 },
    { x: 612, y: 1051 },
    { x: 627, y: 1050 },
    { x: 644, y: 1045 },
    { x: 660, y: 1041 },
    { x: 674, y: 1034 },
    { x: 688, y: 1028 },
    { x: 703, y: 1021 },
    { x: 718, y: 1013 },
    { x: 734, y: 1009 },
    { x: 754, y: 1004 },
    { x: 770, y: 998 },
    { x: 785, y: 992 },
    { x: 800, y: 987 },
    { x: 815, y: 983 }
];
// Define the initial position of the bus on the line
let busPosition = 0;

// Draw the map image and the bus image on the canvas
function drawMap() {
    // Draw the map image
    context.drawImage(mapImage, 0, 0, canvas.width, canvas.height);
}

// Draw the map image and the bus image on the canvas
function drawBus() {
    context.drawImage(
        busImage,
        lineCoordinates[busPosition].x,
        lineCoordinates[busPosition].y,
        busImage.width,
        busImage.height
    );
}

// display map and bus when refresh
window.onload = function() {
    drawMap();
    drawBus();
    const animationInterval = 50; // milliseconds
    var refreshPosition = setInterval(() => {
        // Update the bus position
        // busPosition = (busPosition + 1) % lineCoordinates.length;
        // console.log(busPosition);
        var myData = [[${VoyageData}]];
        busPosition= myData;
        // Redraw the canvas
        drawMap();
        drawBus();
        if (busPosition > 100) {
            clearInterval(refreshPosition);
        }
    }, animationInterval);
};
var myData = [[${VoyageData}]];
const animationInterval = 50; // milliseconds
var refreshPosition = setInterval(() => {
    // Update the bus position
    // busPosition = (busPosition + 1) % lineCoordinates.length;
    // console.log(busPosition);
    var myData = [[${VoyageData}]];
    busPosition = myData;
    // Redraw the canvas
    drawMap();
    drawBus();
    if (busPosition > 100) {
        clearInterval(refreshPosition);
    }
}, animationInterval);


function StartBus() {

    // Animate the bus image by updating its position on the line at a regular interval
    const animationInterval = 50; // milliseconds
    var refreshPosition = setInterval(() => {
        // Update the bus position
        busPosition = (busPosition + 1) % lineCoordinates.length;
        console.log(busPosition);
        // Redraw the canvas
        drawMap();
        drawBus();
        if (busPosition > 100) {
            clearInterval(refreshPosition);
        }
    }, animationInterval);
}

async function updateData() {
    try {
        const response = await fetch('/bus');
        const data = await response.json();
        document.getElementById('counter').textContent = "asba";
        console.log(data.get);
    } catch (error) {
        console.error(error);
    }
}

setInterval(updateData, 1000);