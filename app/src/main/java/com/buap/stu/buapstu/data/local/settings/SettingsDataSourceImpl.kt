package com.buap.stu.buapstu.data.local.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.buap.stu.buapstu.models.Alumno
import com.buap.stu.buapstu.models.Conductor
import com.buap.stu.buapstu.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataSourceImpl(private val context: Context):SettingsDataSource {

    companion object{
        private const val NAME_PREF_USER="bUSUbUAP"
        private const val KEY_USER_TYPE="KEY_USER_TYPE"
        private const val KEY_USER_UUID="KEY_USER_UUID"
        private const val KEY_USER_STATE="KEY_USER_STATE"
        private const val KEY_USER_PASSWORD="KEY_USER_PASSWORD"
        private const val KEY_USER_NAME="KEY_USER_NAME"
        private const val KEY_USER_EMAIL="KEY_USER_EMAIL"
        private const val KEY_USER_MATRICULA="KEY_USER_MATRICULA"
        private const val KEY_USER_CREDITS="KEY_USER_CREDITS"
        private const val KEY_USER_AFILIADO="KEY_USER_AFILIADO"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME_PREF_USER)
    private val keyType= stringPreferencesKey(KEY_USER_TYPE)
    private val keyUuid= stringPreferencesKey(KEY_USER_UUID)
    private val keyState= booleanPreferencesKey(KEY_USER_STATE)
    private val keyPassword= stringPreferencesKey(KEY_USER_PASSWORD)
    private val keyName= stringPreferencesKey(KEY_USER_NAME)
    private val keyEmail= stringPreferencesKey(KEY_USER_EMAIL)
    private val keyMatricula= stringPreferencesKey(KEY_USER_MATRICULA)
    private val keyCredits= intPreferencesKey(KEY_USER_CREDITS)
    private val keyAfiliado= stringPreferencesKey(KEY_USER_AFILIADO)

    override  fun getUser(): Flow<User> = context.dataStore.data.map { pref->
        val typeUser=pref[keyType]?:""
        if(typeUser=="Alumno"){
            Alumno(
                uid = pref[keyUuid]?:"",
                telefono = "",
                estado = pref[keyState]?:false,
                contrasena = pref[keyPassword]?:"",
                nombre_completo = pref[keyName]?:"",
                correo = pref[keyEmail]?:"",
                type= typeUser,
                matricula = pref[keyMatricula]?:"",
                creditos = pref[keyCredits]?:0,
            )
        }else{
            Conductor(
                uid = pref[keyUuid]?:"",
                telefono = "",
                estado = pref[keyState]?:false,
                contrasena = pref[keyPassword]?:"",
                nombre_completo = pref[keyName]?:"",
                correo = pref[keyEmail]?:"",
                type= typeUser,
                numero_afiliacion = pref[keyAfiliado]?:""
            )
        }
    }

    override suspend fun saveUser(user: User) {
        context.dataStore.edit {pref->
            pref[keyType]=user.type
            pref[keyUuid]=user.uid
            pref[keyState]=user.estado
            pref[keyPassword]=user.contrasena
            pref[keyName]=user.nombre_completo
            pref[keyEmail]=user.correo
            pref[keyType]=user.type

            (user as? Alumno)?.let {
                pref[keyMatricula]=user.matricula
                pref[keyCredits]=user.creditos
            }

            (user as? Conductor)?.let {
                pref[keyAfiliado]=it.numero_afiliacion
            }
        }
    }

    override suspend fun clearData() {
        context.dataStore.edit { pref->
            pref.clear()
        }
    }
}