import { ChangeEvent, FormEvent, useState, useEffect } from "react";

interface ArrayItem {
  original_filename: string;
  filename: string;
  file_url: string;
  raw_file_size: number;
  file_size: string;
}

interface FileResponseArray extends Array<ArrayItem> {}

interface FileResponse {
  uploaded_files: FileResponseArray;
}

function UploadFile() {
  const [files, setFiles] = useState<FileList | null>(null);
  const [resp, setResp] = useState([<div></div>, ""]);
  const [canUpload, setCanUpload] = useState<boolean>(
    localStorage.getItem("canUpload") === "true"
  );

  useEffect(() => {
    localStorage.setItem("canUpload", canUpload.toString());
  }, [canUpload]);

  const handleFlipSwitch = async (flipSwitch: string) => {
    const state = canUpload ? 0 : 1;
    console.log("flipSwitch:", flipSwitch, "state:", state);
    try {
      const response = await fetch(`/api/flipswitch/${flipSwitch}/${state}`, {
        method: "GET",
      });

      if (response.ok) {
        const data = await response.json();
        setCanUpload(data.State);
        localStorage.setItem("canUpload", data.State.toString());
      } else {
        console.error("Failed to toggle upload capability");
      }
    } catch (error) {
      console.error("Error toggling upload capability:", error);
    }
  };

  const handleUploadFiles = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (!canUpload) {
      alert("Uploading is currently disabled.");
      return;
    }
    console.log(event);
    console.log(files);
    if (files) {
      console.log(files[0]);
    }
    const formData = new FormData();
    const token = localStorage.getItem("jwtToken");

    if (files && files[0]) {
      for (let i = 0; i < files.length; i++) {
        formData.append("files", files[i]);
      }
    }

    const response = await fetch("/api/file-storage/upload", {
      method: "POST",
      headers: { Authorization: "Bearer " + token },
      body: formData,
    });

    if (response == null) {
      setResp(["File Upload Unsuccessful"]);
      return;
    }

    if (!response.ok) {
      setResp(["There was an error when attempting to upload files"]);
      return;
    }

    const a: FileResponse = await response.json();
    var respString = [];
    respString.push(
      <div>
        <br />
        <br />
      </div>
    );
    for (let i = 0; i < a.uploaded_files.length; i++) {
      respString.push(
        a.uploaded_files[i].original_filename,
        " stored as ",
        a.uploaded_files[i].filename,
        <div>
          <br />
          <br />
        </div>
      );
    }
    console.log(a.uploaded_files[0].filename);
    setResp(respString);
  };

  const updateFiles = (event: ChangeEvent<HTMLInputElement>) => {
    setFiles(event.target.files);
  };

  return (
    <>
      <div>Select Files to Upload</div>
      <form onSubmit={handleUploadFiles}>
        <input
          type="file"
          id="upload"
          onChange={updateFiles}
          multiple
          disabled={!canUpload}
        />
        <input type="submit" disabled={!canUpload} />
      </form>
      {canUpload ? null : <div>Uploading is currently disabled.</div>}
      <div>{resp}</div>
      <button onClick={() => handleFlipSwitch("uploadCapability")}>
        Toggle Upload Capability
      </button>
    </>
  );
}

export default UploadFile;
