## Describe the Bug
When running the `vela version list -a` command, only older versions of KubeVela are displayed, and the latest versions do not appear in the output.

## Steps to Reproduce

1. **Run the `vela version list -a` command** <a id="run-version-list"></a><br>
   ```bash
   vela version list -a
   ```
   <b>Expected Output</b>
    ```
    Older Version: 1.9.0-beta.1
    Older Version: 1.9.0-alpha.5
    Older Version: 1.9.0-alpha.2
    Older Version: 1.9.0-alpha.1
    Older Version: 1.6.0-alpha.6
    Older Version: 1.6.0-alpha.4
    Older Version: 1.6.0-alpha.3
    Older Version: 1.6.0-alpha.2
    Older Version: 1.6.0-alpha.1
   ...
    ```
   ⚠️ Warning: The output is displaying only older versions of KubeVela, and the newer releases are missing.

## Expected Behavior
The command output should list all available KubeVela versions, including the most recent release(s).
## KubeVela Version
- **KubeVela Versions Tested:** v1.9.13 and v1.10.0

## Cluster information
Tested on a K3d Kubernetes cluster
- **Client Version:** v1.31.2
- **Kustomize Version:** v5.4.2
- **Server Version:** v1.30.4+k3s1

## Additional Information

Reference link: [KubeVela Pre-release Installation Guide](https://kubevela.io/docs/platform-engineers/advanced-install/#install-pre-release)