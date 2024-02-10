<template>
    <canvas ref="canvas" id="canvas_plot" width="500" height="500" @click="checkPoint"/>
</template>

<script>
export default {
    data() {
        return {
            ctx: null,
            canvasWidth: 500,
            canvasHeight: 500,
            xAxis: 250,
            yAxis: 250,
            radius: 200,
            rSplit: 200,
        };
    },
    mounted() {
        this.ctx = this.$refs.canvas.getContext('2d'); // получает <canvas>
        this.clearCanvas();
        this.axis();
        this.zone();
        this.r();
    },
    methods: {
        checkPoint(event) {
            const canvas = this.$refs.canvas;

            // получение координат в пикселях
            let x = event.offsetX - this.xAxis;
            let y = -(event.offsetY - this.yAxis);

            // получение масштаба r
            let r = document.getElementById("rInput").value;

            // получаем координаты в масштабах r
            x /= this.rSplit;
            y /= this.rSplit;

            // получаем координаты в заданных масштабах
            x *= r;
            y *= r;

            this.sending(event, x, y, r)
            console.log("тыкается", x, y, r);
            console.log(localStorage.getItem("login"));
        },
        sending(e, x, y, r) {
            e.preventDefault();
            const data = {
                'x': x,
                'y': y,
                'r': r,
                'owner': localStorage.getItem("login")
            }
            this.$axios.post('http://localhost:8080/api/dots', data);
        },
        clearCanvas() {
            this.ctx.clearRect(0, 0, this.canvasWidth, this.canvasHeight);
            // дописать
        },
        axis() {
            this.ctx.beginPath();
            this.ctx.moveTo(this.xAxis, 0);
            this.ctx.lineTo(this.xAxis, this.canvasHeight);
            this.ctx.moveTo(0, this.yAxis);
            this.ctx.lineTo(this.canvasWidth, this.yAxis);
            this.ctx.stroke();
            this.ctx.closePath();
        },
        circle() {
            this.ctx.beginPath();
            this.ctx.arc(this.xAxis, this.yAxis, this.radius/2, Math.PI, -Math.PI/2);
            this.ctx.lineTo(this.xAxis, this.yAxis);
            this.ctx.fillStyle = "rgba(68,128,112,0.47)";
            this.ctx.fill();
            this.ctx.closePath();
        },
        triangle() {
            this.ctx.beginPath();
            let x1 = this.xAxis;
            let y1 = this.yAxis;
            let x2 = this.xAxis;
            let y2 = this.yAxis - this.radius;
            let x3 = this.xAxis + this.radius/2;
            let y3 = this.yAxis;

            this.ctx.moveTo(x1, y1);
            this.ctx.lineTo(x2, y2);
            this.ctx.lineTo(x3, y3);

            this.ctx.fillStyle = "rgba(68,128,112,0.47)";
            this.ctx.fill();
            this.ctx.closePath();
        },
        square() {
            this.ctx.beginPath();
            let x1 = this.xAxis + this.radius/2;
            let y1 = this.yAxis;
            let x2 = this.xAxis + this.radius/2;
            let y2 = this.yAxis + this.radius;
            let x3 = this.xAxis;
            let y3 = this.yAxis + this.radius;
            let x4 = this.xAxis;
            let y4 = this.yAxis;

            this.ctx.moveTo(x1, y1);
            this.ctx.lineTo(x2, y2);
            this.ctx.lineTo(x3, y3);
            this.ctx.lineTo(x4, y4)

            this.ctx.fillStyle = "rgba(68,128,112,0.47)";
            this.ctx.fill();
            this.ctx.closePath();
        },
        zone() {
            this.circle();
            this.triangle();
            this.square();
        },
        r() {
            this.ctx.beginPath();
            this.ctx.fillStyle = "black";
            this.ctx.font = '10px Arial';
            this.ctx.fillText("X", this.canvasWidth - 10, this.yAxis);
            this.ctx.fillText("Y", this.xAxis, + 10);

            this.ctx.font = '15px Arial';
            for (let i = this.radius; i >= -(this.radius); i -= this.radius / 2 ) {
                let litR = " -R"
                let litR2 = " -R/2"
                if (i < this.radius) {
                    litR = " R"
                    litR2 = " R/2"
                }

                if (i === this.radius / 2 || i === -this.radius / 2) {
                    this.ctx.fillText(litR2, this.xAxis - i, this.yAxis);
                    this.ctx.fillText(litR2, this.xAxis, this.yAxis + i);
                } else if (i === this.radius || i === -(this.radius)) {
                    this.ctx.fillText(litR, this.xAxis - i, this.yAxis);
                    this.ctx.fillText(litR, this.xAxis, this.yAxis + i);
                } else {
                    continue;
                }
                this.ctx.fillRect(this.xAxis - i, this.yAxis - 4, 2, 8);
                this.ctx.fillRect(this.xAxis - 4, this.yAxis - i, 8, 2);
            }
            this.ctx.closePath();
        },
    }
}
</script>

<style>
#canvas_plot {
    margin: auto;
    background: #efefaa;
}

canvas {
    display: block;
    margin: 0 auto;
}
</style>