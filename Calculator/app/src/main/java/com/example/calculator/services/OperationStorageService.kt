package com.example.calculator.services

import com.example.calculator.data.CalculatorOperation
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class OperationStorageService {
    private val db = Firebase.firestore

    fun addOperation(
        operation: CalculatorOperation,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val timestamp = System.currentTimeMillis().toString()

        val operationMap = hashMapOf(
            "input" to operation.input,
            "result" to operation.result
        )

        db.collection("operations").document(timestamp).set(operationMap)
            .addOnSuccessListener {
                //Toast.makeText(this, "Неверный формат", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    fun getOperations(onSuccess: (List<CalculatorOperation>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("operations")
            .get()
            .addOnSuccessListener { result ->
                val operations = result.documents.mapNotNull {
                    it.toObject(CalculatorOperation::class.java)
                }
                onSuccess(operations)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun deleteOperations(onSuccess: () -> Unit, onFailure: (Exception) -> Unit){
        db.collection("operations")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) document.reference.delete()
                onSuccess()
            }
    }

}