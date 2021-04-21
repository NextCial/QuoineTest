package com.test.quoinetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main(arrayOf())
    }

    fun main(args:Array<String>){
        val points = arrayOf(intArrayOf(1,1), intArrayOf(2,2), intArrayOf(1,2))
        println("${solution(points).contentToString()}")
    }

    fun solution(input: Array<IntArray>):IntArray{

        val parallelLines = getParallelLines(input);

        val perpendicularLines = find2PerpendicularLines(parallelLines)
            ?: throw Exception("Error: 3 input points can not create a rectangle")


        return getIntersection(perpendicularLines)
    }


    private fun getParallelLines(points: Array<IntArray>):Array<IntArray>{
        //line format: ax + by + c =0
        // (a,b) is normal vector of the line

        val normalVectors = arrayOf(intArrayOf(points[0][1] - points[1][1],points[1][0]-points[0][0]),
                intArrayOf(points[0][1] - points[2][1],points[2][0]-points[0][0]),
                intArrayOf(points[1][1] - points[2][1],points[2][0]-points[1][0]))

        val cFactors = intArrayOf(-normalVectors[0][0]*points[2][0] -normalVectors[0][1]*points[2][1],
                -normalVectors[1][0]*points[1][0] -normalVectors[1][1]*points[1][1],
                -normalVectors[2][0]*points[0][0] -normalVectors[2][1]*points[0][1])

        return arrayOf(intArrayOf(normalVectors[0][0],normalVectors[0][1],cFactors[0]),
                        intArrayOf(normalVectors[1][0],normalVectors[1][1],cFactors[1]),
                        intArrayOf(normalVectors[2][0],normalVectors[2][1],cFactors[2]))
    }

    private fun isPerpendicular(lines:Array<IntArray>):Boolean{
        //line_1: (a1,b1,c1)
        //line_2: (a2,b2,c2)
        // perpendicular condition: a1a2 + b1b2 = 0

        return lines[0][0]*lines[1][0] + lines[0][1]*lines[1][1] == 0
    }

    private fun find2PerpendicularLines(lines:Array<IntArray>): Array<IntArray>? {
        return when {
            isPerpendicular(arrayOf(lines[0],lines[1])) -> {
                arrayOf(lines[0],lines[1])
            }
            isPerpendicular(arrayOf(lines[0],lines[2])) -> {
                arrayOf(lines[0],lines[2])
            }
            isPerpendicular(arrayOf(lines[1],lines[2])) -> {
                arrayOf(lines[1],lines[2])
            }
            else -> null
        }
    }

    private fun getIntersection(lines:Array<IntArray>):IntArray{
        //Resolve equations to  find intersection
        //equation format: ax + by = c
        // line 1: [a1,b1,-c1]
        // line 2: [a2,b2,-c2]

        val D = lines[0][0]*lines[1][1] - lines[1][0]*lines[0][1]
        val Dx = -lines[0][2]*lines[1][1] + lines[1][2]*lines[0][1]
        val Dy = lines[0][0]*(-lines[1][2]) - lines[1][0]*(-lines[0][2])

        if(D == 0){
            throw Exception("Error: 3 input points can not create a rectangle")
        }

        return intArrayOf(Dx/D,Dy/D)
    }
}