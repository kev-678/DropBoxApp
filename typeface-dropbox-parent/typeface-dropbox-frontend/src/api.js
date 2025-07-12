const API_BASE = 'http://localhost:8081/api/files';

export async function getFiles() {
  const res = await fetch(API_BASE);
  if (!res.ok) throw new Error('Failed to fetch files');
  return res.json();
}

export async function uploadFile(file) {
  const formData = new FormData();
  formData.append('file', file);
  const res = await fetch(API_BASE, {
    method: 'POST',
    body: formData,
  });
  if (!res.ok) throw new Error('Upload failed');
  return res.json();
}

export async function downloadFile(uuid) {
  const res = await fetch(`${API_BASE}/${encodeURIComponent(uuid)}/download`);
  if (!res.ok) throw new Error('Download failed');
  return res.blob();
}
