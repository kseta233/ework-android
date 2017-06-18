package com.ework.eduplex.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.utils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimulasiKreditActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tilSimulasiKreditTotalPinjaman)
    TextInputLayout tilSimulasiKreditTotalPinjaman;
    @Bind(R.id.tilSimulasiKreditTenor)
    TextInputLayout tilSimulasiKreditTenor;
    @Bind(R.id.tilSimulasiKreditUangMuka)
    TextInputLayout tilSimulasiKreditUangMuka;
    @Bind(R.id.tilSimulasiKreditBungaPinjaman)
    TextInputLayout tilSimulasiKreditBungaPinjaman;
    @Bind(R.id.tilSimulasiKreditAdminFee)
    TextInputLayout tilSimulasiKreditAdminFee;
    @Bind(R.id.tvSimulasiKreditInstallment)
    TextView tvSimulasiKreditInstallment;
    @Bind(R.id.tvSimulasiKreditAngsuranPertama)
    TextView tvSimulasiKreditAngsuranPertama;
    @Bind(R.id.tvSimulasiKreditAngsuranSelanjutnya)
    TextView tvSimulasiKreditAngsuranSelanjutnya;
    @Bind(R.id.llResult)
    LinearLayout llResult;
    @Bind(R.id.tvSimulasi)
    TextView tvSimulasi;
    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulasi_kredit);
        ButterKnife.bind(this);

        initComponents();
        addInputTextSimulasiKreditWatcher();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is used to initialization general components.
     */
    private void initComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_simulasi_kredit));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        llResult.setVisibility(View.GONE);

        tilSimulasiKreditBungaPinjaman.getEditText().setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3, 2)});
        tilSimulasiKreditUangMuka.getEditText().setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3, 2)});
    }

    /**
     * This method is used to validate credit simulation form.
     *
     * @return Return boolean valid or invalid.
     */
    private boolean validateCreditSimulationForm() {
        if (tilSimulasiKreditTotalPinjaman.getEditText().getText().toString().equals("")) {
            tilSimulasiKreditTotalPinjaman.setError(getResources().getString(R.string.err_harap_diisi));
            return false;
        } else {
            tilSimulasiKreditTotalPinjaman.setError(null);
        }
        if (tilSimulasiKreditTenor.getEditText().getText().toString().equals("")) {
            tilSimulasiKreditTenor.setError(getResources().getString(R.string.err_harap_diisi));
            return false;
        } else {
            tilSimulasiKreditTenor.setError(null);
        }
        if (tilSimulasiKreditUangMuka.getEditText().getText().toString().equals("")) {
            tilSimulasiKreditUangMuka.setError(getResources().getString(R.string.err_harap_diisi));
            return false;
        } else {
            tilSimulasiKreditUangMuka.setError(null);
        }
        if (Double.parseDouble(tilSimulasiKreditUangMuka.getEditText().getText().toString()) > 99) {
            tilSimulasiKreditUangMuka.setError(getResources().getString(R.string.err_percentage_max));
            return false;
        } else {
            tilSimulasiKreditUangMuka.setError(null);
        }
        if (tilSimulasiKreditBungaPinjaman.getEditText().getText().toString().equals("")) {
            tilSimulasiKreditBungaPinjaman.setError(getResources().getString(R.string.err_harap_diisi));
            return false;
        } else {
            tilSimulasiKreditBungaPinjaman.setError(null);
        }
        if (Double.parseDouble(tilSimulasiKreditBungaPinjaman.getEditText().getText().toString()) > 99) {
            tilSimulasiKreditBungaPinjaman.setError(getResources().getString(R.string.err_percentage_max));
            return false;
        } else {
            tilSimulasiKreditBungaPinjaman.setError(null);
        }
        if (tilSimulasiKreditAdminFee.getEditText().getText().toString().equals("")) {
            tilSimulasiKreditAdminFee.setError(getResources().getString(R.string.err_harap_diisi));
            return false;
        } else {
            tilSimulasiKreditAdminFee.setError(null);
        }

        return true;
    }

    /**
     * This method is used to add textwatcher for credit simulation form, to change the nominal format directly.
     */
    private void addInputTextSimulasiKreditWatcher() {

        tilSimulasiKreditAdminFee.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //CONDITION TO HANDLE Rp. 0, only for Admin Fee
                if (s.toString().equals("Rp. 0")) {
                    return;
                }

                if (s.toString().length() == 1 && s.toString().equals("0")) {
                    tilSimulasiKreditAdminFee.getEditText().removeTextChangedListener(this);
                    tilSimulasiKreditAdminFee.getEditText().setText("Rp. 0");
                    tilSimulasiKreditAdminFee.getEditText().addTextChangedListener(this);
                } else {
                    try {
                        String nominal = Utils.getNormalizeNominal(tilSimulasiKreditAdminFee.getEditText().getText().toString()) + "";

                        if (nominal.substring(0, 1).equals("0")) {
                            tilSimulasiKreditAdminFee.getEditText().removeTextChangedListener(this);
                            tilSimulasiKreditAdminFee.getEditText().setText("");
                            tilSimulasiKreditAdminFee.getEditText().addTextChangedListener(this);
                        } else {
                            tilSimulasiKreditAdminFee.getEditText().removeTextChangedListener(this);
                            tilSimulasiKreditAdminFee.getEditText().setText(Utils.getShownNominal(nominal));
                            tilSimulasiKreditAdminFee.getEditText().addTextChangedListener(this);
                            tilSimulasiKreditAdminFee.getEditText().setSelection(tilSimulasiKreditAdminFee.getEditText().getText().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        tilSimulasiKreditAdminFee.getEditText().removeTextChangedListener(this);
                        tilSimulasiKreditAdminFee.getEditText().setText("");
                        tilSimulasiKreditAdminFee.getEditText().addTextChangedListener(this);
                    }
                }
            }
        });

        tilSimulasiKreditTenor.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("0")) {
                    tilSimulasiKreditTenor.getEditText().setText("");
                }
            }
        });


        tilSimulasiKreditTotalPinjaman.getEditText().addTextChangedListener(Utils.getNominalFormatTextWatcher(tilSimulasiKreditTotalPinjaman.getEditText()));
    }

    /**
     * This method is used to calculate credit simulation.
     *
     * @return calculated credit simulation result.
     */
    private double calculateCreditSimulation() {

//        double mUangMuka = Double.parseDouble(tilSimulasiKreditUangMuka.getEditText().getText().toString());
//        double mHarga = Double.parseDouble(Utils.getNormalizeNominal(tilSimulasiKreditTotalPinjaman.getEditText().getText().toString()) + "");
//        double mTenor = Double.parseDouble(tilSimulasiKreditTenor.getEditText().getText().toString());
//        double mBunga = Double.parseDouble(tilSimulasiKreditBungaPinjaman.getEditText().getText().toString());
//
//        //CONDITION TO HANDLE Rp. 0, only for Admin Fee
//        double mAdminFee = 0;
//        if (tilSimulasiKreditAdminFee.getEditText().getText().toString().equals("Rp. 0")) {
//            mAdminFee = 0;
//        } else {
//            mAdminFee = Double.parseDouble(Utils.getNormalizeNominal(tilSimulasiKreditAdminFee.getEditText().getText().toString()) + "");
//        }
//
//        double uangMuka = (mUangMuka / 100) * mHarga;
//        double pokokHutang = 1 + ((mBunga / 100) * mTenor);
//        double angsuran = (mHarga - uangMuka) * pokokHutang / mTenor;
//
//        double pembayaran = uangMuka + angsuran;
//        pembayaran = pembayaran + mAdminFee;
//
//        return pembayaran;

        double mUangMuka = Double.parseDouble(tilSimulasiKreditUangMuka.getEditText().getText().toString());
        double mHarga = Double.parseDouble(Utils.getNormalizeNominal(tilSimulasiKreditTotalPinjaman.getEditText().getText().toString()) + "");
        double mTenor = Double.parseDouble(tilSimulasiKreditTenor.getEditText().getText().toString());
        double mBunga = Double.parseDouble(tilSimulasiKreditBungaPinjaman.getEditText().getText().toString());

        double uangMuka = (mUangMuka / 100) * mHarga;
        double bunga = (mBunga / 100);
        log("creditsimulationlog", uangMuka+" "+bunga);

        //CONDITION TO HANDLE Rp. 0, only for Admin Fee
        double mAdminFee = 0;
        if (tilSimulasiKreditAdminFee.getEditText().getText().toString().equals("Rp. 0")) {
            mAdminFee = 0;
        } else {
            mAdminFee = Double.parseDouble(Utils.getNormalizeNominal(tilSimulasiKreditAdminFee.getEditText().getText().toString()) + "");
        }

        log("creditsimulationlog", mUangMuka+" "+mHarga+" "+mTenor+" "+mBunga+" "+mAdminFee);

        double cicilan = ((mHarga-uangMuka)+(bunga*mHarga*mTenor))/mTenor;

        return uangMuka+mAdminFee+cicilan;
    }

    /* This method is used to calculate credit simulation without admin fee.
    *
    * @return calculated credit simulation result without admin fee.
    */
    private double calculateNormalCreditSimulation() {

//        double mUangMuka = Double.parseDouble(tilSimulasiKreditUangMuka.getEditText().getText().toString());
//        double mHarga = Double.parseDouble(Utils.getNormalizeNominal(tilSimulasiKreditTotalPinjaman.getEditText().getText().toString()) + "");
//        double mTenor = Double.parseDouble(tilSimulasiKreditTenor.getEditText().getText().toString());
//        double mBunga = Double.parseDouble(tilSimulasiKreditBungaPinjaman.getEditText().getText().toString());
//
//        double uangMuka = (mUangMuka / 100) * mHarga;
//        double bunga = (mBunga / 100);
//
//        //CONDITION TO HANDLE Rp. 0, only for Admin Fee
//        double mAdminFee = 0;
//        if (tilSimulasiKreditAdminFee.getEditText().getText().toString().equals("Rp. 0")) {
//            mAdminFee = 0;
//        } else {
//            mAdminFee = Double.parseDouble(Utils.getNormalizeNominal(tilSimulasiKreditAdminFee.getEditText().getText().toString()) + "");
//        }
//
//        double cicilan = ((mHarga-uangMuka)*(bunga*mTenor))/mTenor;
//
//        return cicilan;

        double mUangMuka = Double.parseDouble(tilSimulasiKreditUangMuka.getEditText().getText().toString());
        double mHarga = Double.parseDouble(Utils.getNormalizeNominal(tilSimulasiKreditTotalPinjaman.getEditText().getText().toString()) + "");
        double mTenor = Double.parseDouble(tilSimulasiKreditTenor.getEditText().getText().toString());
        double mBunga = Double.parseDouble(tilSimulasiKreditBungaPinjaman.getEditText().getText().toString());

        double uangMuka = (mUangMuka / 100) * mHarga;
        double bunga = (mBunga / 100);
        log("creditsimulationlog", uangMuka+" "+bunga);

        //CONDITION TO HANDLE Rp. 0, only for Admin Fee
        double mAdminFee = 0;
        if (tilSimulasiKreditAdminFee.getEditText().getText().toString().equals("Rp. 0")) {
            mAdminFee = 0;
        } else {
            mAdminFee = Double.parseDouble(Utils.getNormalizeNominal(tilSimulasiKreditAdminFee.getEditText().getText().toString()) + "");
        }

        log("creditsimulationlog", mUangMuka+" "+mHarga+" "+mTenor+" "+mBunga+" "+mAdminFee);

        double cicilan = ((mHarga-uangMuka)+(bunga*mHarga*mTenor))/mTenor;

        return  cicilan;
    }

    @OnClick(R.id.tvSimulasi)
    public void onClick() {
        if (validateCreditSimulationForm()) {
            tvSimulasiKreditInstallment.setText(tilSimulasiKreditTenor.getEditText().getText().toString() + "X");
            tvSimulasiKreditAngsuranPertama.setText(Utils.getShownNominal(Math.round(calculateCreditSimulation()) + ""));
            tvSimulasiKreditAngsuranSelanjutnya.setText(Utils.getShownNominal(Math.round(calculateNormalCreditSimulation()) + ""));

            llResult.setVisibility(View.VISIBLE);
            hideSoftKeyboard();
        }
    }

    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

    }
}
