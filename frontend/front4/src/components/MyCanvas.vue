<template>
    <canvas ref="canvas" id="canvas_plot" width="500" height="500" @click="checkPoint"/>
</template>

<script>
import { ref, onMounted, watchEffect } from 'vue';
import {state} from "@/components/InputForm.vue";

export default {
    // при выборе r в InputForm устанавливает в rState новое значение
    setup() {
        const rState = ref(state.rValue);

        onMounted(() => {
            watchEffect(() => {
                rState.value = state.rValue;
            });
        });

        return { rState };
    },
    data() {
        return {
            ctx: null,
            canvasWidth: 500,
            canvasHeight: 500,
            xAxis: 250,
            yAxis: 250,
            radius: 200,
            rSplit: 200,
            resultsArray: [],
            // rValue: -5,
            rState: state.rValue
        };
    },
    mounted() {
        this.ctx = this.$refs.canvas.getContext('2d'); // получает <canvas>
        setTimeout(() => {
            this.dotSend();
            console.log("r", state.rValue);
        }, 50);
    },
    // отслеживает изменение в переменной rState
    watch: {
        rState: function () {
            this.dotSend();
        }
    },
    methods: {
        // записывает в массив resultsArray все координаты из таблицы
        tableValues() {
            this.resultsArray.length = 0;

            const table = this.$parent.$refs.resultTable;

            for (let i = 1; i < table.rows.length; i++) {
                const row = table.rows[i];

                const x = row.cells[0].innerText;
                const y = row.cells[1].innerText;
                const r = row.cells[2].innerText;
                const result = row.cells[3].innerText;

                const point = { x, y, r, result };
                this.resultsArray.push(point);
            }
        },
        // Отрисовка значений из таблицы
        dotSend() {
            this.clearCanvas();
            this.tableValues();
            console.log("table", this.resultsArray);

            this.resultsArray.forEach(result => {
                this.dot(result);
            });
        },
        // отрисовка точки
        dot(result) {
            let x = result.x;
            let y = result.y;
            let r = state.rValue;

            let xValue = x / r * this.rSplit + this.xAxis - 2;
            let yValue = - (y / r * this.rSplit - this.yAxis + 2);

            let checkCircle;
            let checkTriangle;
            let checkRectangle;

            // дописать для отрицательных r
            if (r >= 0) {
                checkCircle = x <= 0 && y >= 0 && ((x*x + y*y) <= (r/2)*(r/2));
                checkTriangle = x >= 0 && y >= 0 && y <= (-2*x + r);
                checkRectangle = x >= 0 && x <= r/2 && y <= 0 && y >= -r;
            } else {
                checkCircle = x >= 0 && y <= 0 && x*x + y*y <= (r/2)*(r/2);
                checkTriangle = x <= 0 && y <= 0 && y >= (-2*x + r);
                checkRectangle = x <= 0 && x >= r/2 && y >= 0 && y <= -r;
            }


            if (checkCircle || checkTriangle || checkRectangle) {
                this.ctx.beginPath();
                this.ctx.fillStyle = "blue"
                this.ctx.fillRect(xValue, yValue, 4, 4,)
                this.ctx.closePath();
            } else {
                this.ctx.beginPath();
                this.ctx.fillStyle = "red"
                this.ctx.fillRect(xValue, yValue, 4, 4,)
                this.ctx.closePath();
            }
        },

        // получение координат по клику
        checkPoint(event) {
            this.dotSend();
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
        },
        // отправка координат на сервер
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
        // очистка канваса
        clearCanvas() {
            this.ctx.clearRect(0, 0, this.canvasWidth, this.canvasHeight);
            this.axis();
            this.zone();
            this.r();
        },

        // отрисовка канваса
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