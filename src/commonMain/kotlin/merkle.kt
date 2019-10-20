package leapcore

sealed class MerkleTree {
    abstract val hash: Bytes32;
}

data class Leaf(override val hash: Bytes32): MerkleTree()
data class Node(override val hash: Bytes32, val left: MerkleTree, val right: MerkleTree): MerkleTree()

fun foldTree(level: List<MerkleTree>): MerkleTree =
    when (level.size) {
        1 -> level[0]
        else -> foldTree(level.chunked(2).map {(left, right) ->
            Node(hash(left.hash + right.hash), left, right)
        })
    }

fun getProof(merkleTree: MerkleTree, path: List<Boolean>, level: Int): MerkleTree =
    when(merkleTree) {
        is Leaf -> merkleTree
        is Node -> when(path[level]) {
            false -> Node(merkleTree.hash, getProof(merkleTree.left, path, level+1), Leaf(merkleTree.right.hash))
            true -> Node(merkleTree.hash, Leaf(merkleTree.left.hash), getProof(merkleTree.right, path, level+1))
        }
    }