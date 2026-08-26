import React from 'react';

function ResultCard({ result, onViewHistory }) {
  if (!result) return null;

  const decisionColor = {
    APPROVE: '#2e7d32',
    MANUAL_REVIEW: '#f9a825',
    REJECT: '#c62828',
  }[result.decision];

  return (
    <div className="card">
      <h2>Evaluation Result</h2>
      <p><strong>Applicant ID:</strong> {result.applicantId}</p>
      <p><strong>Risk Score:</strong> {result.riskScore} / 100</p>
      <p>
        <strong>Decision:</strong>{' '}
        <span style={{ color: decisionColor, fontWeight: 'bold' }}>
          {result.decision.replace('_', ' ')}
        </span>
      </p>
      <p><strong>Reasoning:</strong> {result.reasoning}</p>
      <button onClick={() => onViewHistory(result.applicantId)}>View History</button>
    </div>
  );
}

export default ResultCard;
