package br.com.fideliza.ui.common

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.fideliza.databinding.FragmentRegisterCompanyBinding
import br.com.fideliza.firebase.auth.AuthCallBack
import br.com.fideliza.firebase.auth.FirebaseAuthManager
import br.com.fideliza.servidor.ConexaoServidor
import br.com.fideliza.servidor.ServerCallback
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import org.bson.Document
import java.lang.Exception
import android.text.Editable
import android.text.TextWatcher

class RegisterCompanyFragment : Fragment(), ServerCallback {

    private var _binding: FragmentRegisterCompanyBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuthManager: FirebaseAuthManager
    private lateinit var firebaseUser: FirebaseUser

    private lateinit var nome: String
    private lateinit var email: String
    private lateinit var cnpj: String
    private lateinit var senha: String
    private lateinit var senha2: String
    private lateinit var empresaDocument: Document

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegisterCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuthManager = FirebaseAuthManager()

        binding.btnCadastrarEmpresa.setOnClickListener {
            // Coleta de dados do formulário
            email = binding.etEmailCadastro.text.toString()
            senha = binding.etSenhaCadastro.text.toString()
            senha2 = binding.etSenhaCadastroDenovo.text.toString()

            nome = binding.etNomeEmpresa.text.toString()
            cnpj = binding.etCnpj.text.toString()

            // Validação simples dos campos
            if (email.isEmpty() || senha.isEmpty() || senha2.isEmpty() || nome.isEmpty() || cnpj.isEmpty()) {
                Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Verifica se as senhas correspondem e cadastra
            if (senha == senha2) {
                cadastrarEmpresa(email, senha)
            } else{
                binding.etSenhaCadastroDenovo.error = "As senhas são diferentes"
                binding.etSenhaCadastro.error = "As senhas são diferentes"
            }
        }

        binding.btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Adicionar máscara para CNPJ
        binding.etCnpj.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return

                val textoSemMascara = s.toString().replace(Regex("[^\\d]"), "")
                val textoComMascara = aplicarMascaraCnpj(textoSemMascara)

                isUpdating = true
                binding.etCnpj.setText(textoComMascara)
                binding.etCnpj.setSelection(textoComMascara.length)
                isUpdating = false
            }
        })
    }

    private fun cadastrarEmpresa(email: String, password: String) {
        firebaseAuthManager.createAccount(email, password, object : AuthCallBack() {
            override fun createSuccess(result: AuthResult?) {
                firebaseUser = result?.user ?: return
                val userId = firebaseUser.uid

                // Criação do documento da empresa
                empresaDocument = Document().apply {
                    append("nome", nome)
                    append("cnpj", cnpj)
                    append("email", email)
                    append("firebaseUID", userId)
                }

                // Envio dos dados para o servidor usando a interface ServerCallback
                ConexaoServidor.conexao("1;${empresaDocument.toJson()};empresas", this@RegisterCompanyFragment)
            }

            override fun onFailure(exception: Exception?) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "Falha ao criar nova empresa: ${exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResult(resposta: String) {
        activity?.runOnUiThread {
            Log.d("CadastroEmpresa", resposta)
            if (resposta.contains("Sucesso")) {
                Toast.makeText(context, "Empresa cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
                val action = RegisterCompanyFragmentDirections.actionRegisterCompanyFragmentToVerificationFragment(firebaseUser.uid)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Falha ao cadastrar empresa: $resposta", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Função para aplicar máscara de CPF
    private fun aplicarMascaraCnpj(texto: String): String {
        return texto.replace(Regex("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})"), "$1.$2.$3/$4-$5")
    }

}