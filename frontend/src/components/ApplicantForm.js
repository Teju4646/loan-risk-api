import React, { useState } from 'react';
import { submitApplicant } from '../api';

function ApplicantForm({ onResult }) {
  const [form, setForm] = useState({
    fullName: '',
    annualIncome: '',
    creditScore: '',
    existingMonthlyDebt: '',
    requestedLoanAmount: '',
    employmentYears: '',
    employmentStatus: 'EMPLOYED',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const payload = {
        ...form,
        annualIncome: parseFloat(form.annualIncome),
        creditScore: parseInt(form.creditScore, 10),
        existingMonthlyDebt: parseFloat(form.existingMonthlyDebt),
        requestedLoanAmount: parseFloat(form.requestedLoanAmount),
        employmentYears: parseInt(form.employmentYears, 10),
      };
      const response = await submitApplicant(payload);
      onResult(response.data);
    } catch (err) {
      setError(err.response?.data?.error || 'Submission failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card">
      <h2>Loan Application</h2>
      <form onSubmit={handleSubmit}>
        <label>Full Name</label>
        <input name="fullName" value={form.fullName} onChange={handleChange} required />

        <label>Annual Income ($)</label>
        <input name="annualIncome" type="number" value={form.annualIncome} onChange={handleChange} required />

        <label>Credit Score (300-850)</label>
        <input name="creditScore" type="number" min="300" max="850" value={form.creditScore} onChange={handleChange} required />

        <label>Existing Monthly Debt ($)</label>
        <input name="existingMonthlyDebt" type="number" value={form.existingMonthlyDebt} onChange={handleChange} required />

        <label>Requested Loan Amount ($)</label>
        <input name="requestedLoanAmount" type="number" value={form.requestedLoanAmount} onChange={handleChange} required />

        <label>Employment Years</label>
        <input name="employmentYears" type="number" value={form.employmentYears} onChange={handleChange} required />

        <label>Employment Status</label>
        <select name="employmentStatus" value={form.employmentStatus} onChange={handleChange}>
          <option value="EMPLOYED">Employed</option>
          <option value="SELF_EMPLOYED">Self-Employed</option>
          <option value="UNEMPLOYED">Unemployed</option>
        </select>

        {error && <p className="error">{error}</p>}
        <button type="submit" disabled={loading}>
          {loading ? 'Evaluating...' : 'Submit Application'}
        </button>
      </form>
    </div>
  );
}

export default ApplicantForm;
